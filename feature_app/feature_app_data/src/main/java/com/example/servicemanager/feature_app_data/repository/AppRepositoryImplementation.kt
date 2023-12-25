package com.example.servicemanager.feature_app_data.repository

import com.example.servicemanager.feature_app_data.local.AppDatabaseDao
import com.example.servicemanager.feature_app_data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_data.R
import com.example.logger.AppLogger
import com.example.logger.EventLogType
import com.example.servicemanager.feature_app_data.mappers.toEstStateEntity
import com.example.servicemanager.feature_app_data.mappers.toHospitalEntity
import com.example.servicemanager.feature_app_data.mappers.toInspectionStateEntity
import com.example.servicemanager.feature_app_data.mappers.toRepairStateEntity
import com.example.servicemanager.feature_app_data.mappers.toTechnicianEntity
import com.example.servicemanager.feature_app_data.mappers.toUserTypeEntity
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_app_domain.model.UserType
import kotlinx.coroutines.flow.*

class  AppRepositoryImplementation(
    private val appDatabaseDao: AppDatabaseDao,
    private val firebaseApi: AppFirebaseApi,
    private val appLogger: AppLogger<Any>
): AppRepository {

    /* ********************************* SIGNATURES ********************************************* */
    override fun getSignature(signatureId: String): Flow<Resource<ByteArray>> {
        return firebaseApi.getSignature(signatureId)
    }
    override fun updateSignature(signatureId: String, byteArray: ByteArray): Flow<Resource<String>> {
        return firebaseApi.uploadSignature(signatureId, byteArray)
    }
    override fun createSignature(signatureId: String, byteArray: ByteArray): Flow<Resource<String>> {
        return firebaseApi.uploadSignature(signatureId, byteArray)
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
        }
    }

    override fun updateHospital(hospital: Hospital): Flow<Resource<String>> {
        return firebaseApi.updateHospital(hospital)
    }

    override fun createHospital(hospital: Hospital): Flow<Resource<String>> {
        return firebaseApi.createHospital(hospital)
    }
    override fun createHospitalWithId(hospital: Hospital): Flow<Resource<String>> {
        return firebaseApi.createHospitalWithId(hospital)
    }

    override fun deleteHospital(hospitalId: String): Flow<Resource<String>> {
        return firebaseApi.deleteHospital(hospitalId)
    }

    /* ********************************* TECHNICIANS ******************************************** */
    override fun getTechnicianList() = flow {
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
        }
    }

    override fun createTechnician(technician: Technician): Flow<Resource<String>> {
        return firebaseApi.createTechnician(technician)
    }

    override fun deleteTechnician(technicianId: String): Flow<Resource<String>> {
        return  firebaseApi.deleteTechnician(technicianId)
    }

    override fun updateTechnician(technician: Technician): Flow<Resource<String>> {
        return firebaseApi.updateTechnician(technician)
    }

    /* ********************************* INSPECTION STATES ************************************** */
    override fun getInspectionStateList() = flow {
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
    }

    override fun createInspectionState(inspectionState: InspectionState): Flow<Resource<String>> {
        return firebaseApi.createInspectionState(inspectionState)
    }

    override fun deleteInspectionState(inspectionStateId: String): Flow<Resource<String>> {
        return firebaseApi.deleteInspectionState(inspectionStateId)
    }

    override fun updateInspectionState(inspectionState: InspectionState): Flow<Resource<String>> {
        return firebaseApi.updateInspectionState(inspectionState)
    }

    /* ********************************* REPAIR STATES ****************************************** */
    override fun getRepairStateList() = flow<Resource<List<RepairState>>> {
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
        if(list.isNotEmpty()) {
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
    }

    override fun createRepairState(repairState: RepairState): Flow<Resource<String>> {
        return firebaseApi.createRepairState(repairState)
    }

    override fun deleteRepairState(repairStateId: String): Flow<Resource<String>> {
        return firebaseApi.deleteRepairState(repairStateId)
    }

    override fun updateRepairState(repairState: RepairState): Flow<Resource<String>> {
        return firebaseApi.updateRepairState(repairState)
    }

    /* ********************************* EST STATES ********************************************* */
    override fun getEstStateList() = flow<Resource<List<EstState>>> {
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
    }

    override fun createEstState(estState: EstState): Flow<Resource<String>> {
        return createEstState(estState)
    }

    override fun deleteEstState(estStateId: String): Flow<Resource<String>> {
        return deleteRepairState(estStateId)
    }

    override fun updateEstState(estState: EstState): Flow<Resource<String>> {
        return updateEstState(estState)
    }

    /* ********************************* USER *************************************************** */
    override fun getUser(userId: String): Flow<Resource<User>> = flow {
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
    }

    /* ********************************* USER TYPES ********************************************* */

    override fun getUserTypeList(): Flow<Resource<List<UserType>>> = flow {
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
    }

    override fun createUserType(userType: UserType): Flow<Resource<String>> {
        //appLogger.logUserType(EventLogType.NewRecordLog, )
        return firebaseApi.createUserType(userType)
    }

    override fun deleteUserType(userTypeId: String): Flow<Resource<String>> {
        return firebaseApi.deleteUserType(userTypeId)
    }

    override fun updateUserType(userType: UserType): Flow<Resource<String>> {
        return updateUserType(userType)
    }


}
