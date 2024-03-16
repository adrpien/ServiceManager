package com.example.servicemanager.common_data.repository

import com.example.servicemanager.common_data.local.room.AppDatabaseDao
import com.example.servicemanager.common_data.remote.AppFirebaseApi
import com.example.servicemanager.common_domain.repository.AppRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_data.R
import com.example.logger.AppLogger
import com.example.servicemanager.common_data.local.local_image_source.LocalImageSource
import com.example.servicemanager.common_data.mappers.toCachedSignatureEntity
import com.example.servicemanager.common_data.mappers.toEstStateEntity
import com.example.servicemanager.common_data.mappers.toHospitalEntity
import com.example.servicemanager.common_data.mappers.toInspectionStateEntity
import com.example.servicemanager.common_data.mappers.toRepairStateEntity
import com.example.servicemanager.common_data.mappers.toTechnicianEntity
import com.example.servicemanager.common_data.mappers.toUserTypeEntity
import com.example.servicemanager.common_domain.model.CachedSignature
import com.example.servicemanager.common_domain.model.EstState
import com.example.servicemanager.common_domain.model.Hospital
import com.example.servicemanager.common_domain.model.InspectionState
import com.example.servicemanager.common_domain.model.RepairState
import com.example.servicemanager.common_domain.model.Technician
import com.example.servicemanager.common_domain.model.User
import com.example.servicemanager.common_domain.model.UserType
import com.example.servicemanager.common_domain.util.CacheOperationType
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.*

class  AppRepositoryImplementation(
    private val appDatabaseDao: AppDatabaseDao,
    private val firebaseApi: AppFirebaseApi,
    private val appLogger: AppLogger<Any>,
    private val localImageSource: LocalImageSource
): AppRepository {

    /* *************************************** SYNC ********************************************* */
    override suspend fun getCachedSignatureList(): Resource<List<CachedSignature>> {
        return try {
            Resource(
                ResourceState.SUCCESS,
                appDatabaseDao.getCachedSignatureList().map { it.toCachedSignature() },
                UiText.StringResource(R.string.cached_signatures_fetching_success)
            )
        } catch (e: Exception) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }


    override suspend fun syncSignature(signature: CachedSignature): Resource<String> {
        return firebaseApi.uploadSignature(signature.signatureId, localImageSource.getPhotoWithUri(signature.uri) ?: byteArrayOf())
    }

    override suspend fun deleteCachedSignature(signatureId: String): Resource<String> {
        return try {
            appDatabaseDao.deleteCachedSignature(signatureId)
            Resource(
                ResourceState.SUCCESS,
                signatureId,
                UiText.StringResource(R.string.cached_signature_deleted)
            )
        } catch (e: Exception){
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

    override suspend fun insertCachedSignature(cachedSignature: CachedSignature): Resource<String> {
        return try {
            appDatabaseDao.insertCachedSignature(cachedSignature.toCachedSignatureEntity())
            Resource(
                ResourceState.SUCCESS,
                cachedSignature.signatureId,
                UiText.StringResource(R.string.cached_signature_deleted)
            )
        } catch (e: Exception){
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

    /* ********************************* SIGNATURES ********************************************* */
    override fun getSignature(signatureId: String): Flow<Resource<ByteArray>> = flow {
        try {
            var localSignatureResource: Resource<ByteArray> = localImageSource.getPhotoWithName("$signatureId.jpg")
            if (localSignatureResource.resourceState == ResourceState.SUCCESS) {
                emit(
                    Resource(
                        ResourceState.LOADING,
                        localSignatureResource.data,
                        UiText.StringResource(R.string.locally_cashed_list)
                    )
                )
            } else if (localSignatureResource.resourceState == ResourceState.ERROR) {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        localSignatureResource.message
                    )
                )
            }
            val remoteSignatureResource = firebaseApi.getSignature(signatureId)
            emit(remoteSignatureResource)

        } catch (e: Exception) {
            emit(
                Resource(
                ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.unknown_error)
            )
            )
        }
    }
    override suspend fun updateSignature(signatureId: String, byteArray: ByteArray): Resource<String> {
        return try {
            val uri = localImageSource.savePhotoLocally(byteArray, signatureId)
            val result = firebaseApi.uploadSignature(signatureId, byteArray)
            if(result.resourceState == ResourceState.ERROR) {
                appDatabaseDao.insertCachedSignature(CachedSignature(
                    signatureId = signatureId,
                    operationType = CacheOperationType.Update(),
                    uri = uri
                ).toCachedSignatureEntity())
            }
            result
        } catch (e: Exception) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error))
        }
    }
    override suspend fun createSignature(signatureId: String, byteArray: ByteArray): Resource<String> {
        return try {
            localImageSource.savePhotoLocally(byteArray, signatureId)
            firebaseApi.uploadSignature(signatureId, byteArray)
        } catch (e: FirebaseFirestoreException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.check_internet_connection)
            )
        }
    }

    /* ********************************* HOSPITALS ********************************************* */
    override fun getHospitalList() = flow {
        var hospitalList: List<Hospital>
        hospitalList = appDatabaseDao.getHospitalList().map { it.toHospital() }
        emit(
            Resource(
                ResourceState.LOADING,
                hospitalList,
                UiText.StringResource(R.string.locally_cached_list)
            )
        )
        try {
            val list = firebaseApi.getHospitalList()
            if(list.isNotEmpty()) {
                appDatabaseDao.deleteAllHospitals()
                for (hospital in list) {
                    appDatabaseDao.insertHospital(hospital.toHospitalEntity())
                }
                hospitalList = appDatabaseDao.getHospitalList().map { it.toHospital() }
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        hospitalList,
                        UiText.StringResource(R.string.device_list_fetching_finished)
                    )
                )
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        hospitalList,
                        UiText.StringResource(R.string.locally_cached_list)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
            )
            )
        }

    }

    override suspend fun updateHospital(hospital: Hospital): Resource<String> {
        if (hospital.hospitalId == ""){
            throw IllegalArgumentException("hospitalId can not be empty")
        }
        return firebaseApi.updateHospital(hospital)
    }
    override suspend fun createHospital(hospital: Hospital): Resource<String> {
        if (hospital.hospitalId == ""){
            throw IllegalArgumentException("hospitalId can not be empty")
        }
        return firebaseApi.createHospital(hospital)
    }
    override suspend fun createHospitalWithId(hospital: Hospital): Resource<String> {
        if (hospital.hospitalId == ""){
            throw IllegalArgumentException("hospitalId can not be empty")
        }
        return firebaseApi.createHospitalWithId(hospital)
    }

    override suspend fun deleteHospital(hospitalId: String): Resource<String> {
        if (hospitalId == ""){
            throw IllegalArgumentException("hospitalId can not be empty")
        }
        return firebaseApi.deleteHospital(hospitalId)
    }

    /* ********************************* TECHNICIANS ******************************************** */
    override fun getTechnicianList() = flow {
        try {
            var technicianList: List<Technician>
            technicianList = appDatabaseDao.getTechnicianList().map { it.toTechnician() }
            emit(
                Resource(
                    ResourceState.LOADING,
                    technicianList,
                    UiText.StringResource(R.string.locally_cached_list)
                )
            )
            val list = firebaseApi.getTechnicianList()
            if(list.isNotEmpty()) {
                appDatabaseDao.deleteAllTechnicians()
                for (technician in list) {
                    appDatabaseDao.insertTechnician(technician.toTechnicianEntity())
                }
                technicianList = appDatabaseDao.getTechnicianList().map { it.toTechnician() }
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        technicianList,
                        UiText.StringResource(R.string.device_list_fetching_finished)
                    )
                )
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        technicianList,
                        UiText.StringResource(R.string.locally_cached_list)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            )
        }

    }

    override suspend fun createTechnician(technician: Technician): Resource<String> {
        if (technician.technicianId == ""){
            throw IllegalArgumentException("technicianId can not be empty")
        }
        return firebaseApi.createTechnician(technician)
    }
    override suspend fun createTechnicianWithId(technician: Technician): Resource<String> {
        if (technician.technicianId == ""){
            throw IllegalArgumentException("technicianId can not be empty")
        }
        return firebaseApi.createTechnicianWithId(technician)
    }
    override suspend fun deleteTechnician(technicianId: String): Resource<String> {
        if (technicianId == ""){
            throw IllegalArgumentException("technicianId can not be empty")
        }
        return  firebaseApi.deleteTechnician(technicianId)
    }

    override suspend fun updateTechnician(technician: Technician): Resource<String> {
        if (technician.technicianId == ""){
            throw IllegalArgumentException("technicianId can not be empty")
        }
        return firebaseApi.updateTechnician(technician)
    }

    /* ********************************* INSPECTION STATES ************************************** */
    override fun getInspectionStateList() = flow {
        try {
            var inspectionStateList: List<InspectionState>
            inspectionStateList = appDatabaseDao.getInspectionStateList().map { it.toInspectionState() }
            emit(
                Resource(
                    ResourceState.LOADING,
                    inspectionStateList,
                    UiText.StringResource(R.string.locally_cached_list)
                )
            )
            val list = firebaseApi.getInspectionStateList()
            if(list.isNotEmpty()) {
                appDatabaseDao.deleteAllInspectionStates()
                for (inspectionState in list) {
                    appDatabaseDao.insertInspectionState(inspectionState.toInspectionStateEntity())
                }
                inspectionStateList = appDatabaseDao.getInspectionStateList().map { it.toInspectionState() }
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        inspectionStateList,
                        UiText.StringResource(R.string.inspection_state_list_fetching_finished)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
             emit(
                 Resource(
                     ResourceState.ERROR,
                     null,
                     UiText.StringResource(R.string.check_internet_connection)
             )
             )
        }

    }

    override suspend fun createInspectionState(inspectionState: InspectionState): Resource<String> {
        if (inspectionState.inspectionStateId == ""){
            throw IllegalArgumentException("inspectionStateId can not be empty")
        }
        return firebaseApi.createInspectionState(inspectionState)
    }
    override suspend fun createInspectionStateWithId(inspectionState: InspectionState): Resource<String> {
        if (inspectionState.inspectionStateId == ""){
            throw IllegalArgumentException("inspectionStateId can not be empty")
        }
        return firebaseApi.createInspectionStateWithId(inspectionState)
    }

    override suspend fun deleteInspectionState(inspectionStateId: String): Resource<String> {
        if (inspectionStateId == ""){
            throw IllegalArgumentException("inspectionStateId can not be empty")
        }
        return firebaseApi.deleteInspectionState(inspectionStateId)
    }

    override suspend fun updateInspectionState(inspectionState: InspectionState): Resource<String> {
        if (inspectionState.inspectionStateId == ""){
            throw IllegalArgumentException("inspectionStateId can not be empty")
        }
        return firebaseApi.updateInspectionState(inspectionState)
    }

    /* ********************************* REPAIR STATES ****************************************** */
    override fun getRepairStateList() = flow<Resource<List<RepairState>>> {
        try {
            var repairStateList: List<RepairState>
            repairStateList = appDatabaseDao.getRepairStateList().map { it.toRepairState() }
            emit(
                Resource(
                    ResourceState.LOADING,
                    repairStateList,
                    UiText.StringResource(R.string.locally_cached_list)
                )
            )
            val list = firebaseApi.getRepairStateList()
            if (list.isNotEmpty()) {
                appDatabaseDao.deleteAllRepairStates()
                for (repairState in list) {
                    appDatabaseDao.insertRepairState(repairState.toRepairStateEntity())
                }
                repairStateList = appDatabaseDao.getRepairStateList().map { it.toRepairState() }
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        repairStateList,
                        UiText.StringResource(R.string.inspection_state_list_fetching_finished)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            )
        }

    }

    override suspend fun createRepairState(repairState: RepairState): Resource<String> {
        if (repairState.repairStateId == ""){
            throw IllegalArgumentException("repairStateId can not be empty")
        }
        return firebaseApi.createRepairState(repairState)
    }
    override suspend fun createRepairStateWithId(repairState: RepairState): Resource<String> {
        if (repairState.repairStateId == "") {
            throw IllegalArgumentException("repairStateId can not be empty")
        }
        return firebaseApi.createRepairStateWithId(repairState)
    }
    override suspend fun deleteRepairState(repairStateId: String): Resource<String> {
        if (repairStateId == ""){
            throw IllegalArgumentException("repairStateId can not be empty")
        }
        return firebaseApi.deleteRepairState(repairStateId)
    }
    override suspend fun updateRepairState(repairState: RepairState): Resource<String> {
        if (repairState.repairStateId == ""){
            throw IllegalArgumentException("repairStateId can not be empty")
        }
        return firebaseApi.updateRepairState(repairState)
    }

    /* ********************************* EST STATES ********************************************* */
    override fun getEstStateList() = flow<Resource<List<EstState>>> {
        try {
            var estStateList: List<EstState>
            estStateList = appDatabaseDao.getEstStateList().map { it.toEstState() }
            emit(
                Resource(
                    ResourceState.LOADING,
                    estStateList,
                    UiText.StringResource(R.string.locally_cached_list)
                )
            )
            val list = firebaseApi.getEstStateList()
            if(list.isNotEmpty()) {
                appDatabaseDao.deleteAllEstStates()
                for (estState in list) {
                    appDatabaseDao.insertEstState(estState.toEstStateEntity())
                }
                estStateList = appDatabaseDao.getEstStateList().map { it.toEstState() }
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        estStateList,
                        UiText.StringResource(R.string.device_list_fetching_finished)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            )
        }

    }

    override suspend fun createEstState(estState: EstState): Resource<String> {
        if (estState.estStateId == ""){
            throw IllegalArgumentException("estStateId can not be empty")
        }
        return firebaseApi.createEstState(estState)
    }
    override suspend fun createEstStateWithId(estState: EstState): Resource<String> {
        if (estState.estStateId == ""){
            throw IllegalArgumentException("estStateId can not be empty")
        }
        return firebaseApi.createEstStateWithId(estState)
    }
    override suspend fun deleteEstState(estStateId: String): Resource<String> {
        if (estStateId == ""){
            throw IllegalArgumentException("estStateId can not be empty")
        }
        return firebaseApi.deleteEstState(estStateId)
    }
    override suspend fun updateEstState(estState: EstState): Resource<String> {
        if (estState.estStateId == ""){
            throw IllegalArgumentException("estStateId can not be empty")
        }
        return updateEstState(estState)
    }

    /* ********************************* USER *************************************************** */
    override fun getUser(userId: String): Flow<Resource<User>> = flow {
        try {
            if (userId == ""){
                throw IllegalArgumentException("userId can not be empty")
            }
            var user = firebaseApi.getUser(userId)
            if (user != null){
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        user,
                        UiText.StringResource(R.string.user_fetching_finished)
                    )
                )
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        user,
                        UiText.StringResource(R.string.user_fetching_error)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
            emit(
                Resource(
                ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
            )
            )
        }

    }

    /* ********************************* USER TYPES ********************************************* */

    override fun getUserTypeList(): Flow<Resource<List<UserType>>> = flow {
        try {
            var userTypeList: List<UserType>
            userTypeList = appDatabaseDao.getUserTypeList().map { it.toUserType() }
            emit(
                Resource(
                    ResourceState.LOADING,
                    userTypeList,
                    UiText.StringResource(R.string.locally_cached_list)
                )
            )
            val list = firebaseApi.getUserTypeList()
            if(list.isNotEmpty()) {
                appDatabaseDao.deleteAllUserTypes()
                for (userType in list) {
                    appDatabaseDao.insertUserType(userType.toUserTypeEntity())
                }
                userTypeList = appDatabaseDao.getUserTypeList().map { it.toUserType() }
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        userTypeList,
                        UiText.StringResource(R.string.user_types_list_fetching_finished)
                    )
                )
            }
        } catch (e: FirebaseFirestoreException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            )
        }
    }

    override suspend fun createUserType(userType: UserType): Resource<String> {
        if (userType.userTypeId == ""){
            throw IllegalArgumentException("userTypeId can not be empty")
        }
        return firebaseApi.createUserType(userType)
    }

    override suspend fun createUserTypeWithId(userType: UserType): Resource<String> {
        if (userType.userTypeId == ""){
            throw IllegalArgumentException("userTypeId can not be empty")
        }
        return firebaseApi.createUserTypeWithId(userType)
    }

    override suspend fun deleteUserType(userTypeId: String): Resource<String> {
        if (userTypeId == ""){
            throw IllegalArgumentException("userTypeId can not be empty")
        }
        return firebaseApi.deleteUserType(userTypeId)
    }

    override suspend fun updateUserType(userType: UserType): Resource<String> {
        if (userType.userTypeId == ""){
            throw IllegalArgumentException("userTypeId can not be empty")
        }
        return firebaseApi.updateUserType(userType)
    }


}
