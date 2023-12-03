package com.example.servicemanager.feature_app_data.repository

import com.example.servicemanager.feature_app_data.local.AppDatabaseDao
import com.example.servicemanager.feature_app_data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_data.mappers.toEstStateEntity
import com.example.servicemanager.feature_app_data.mappers.toHospitalEntity
import com.example.servicemanager.feature_app_data.mappers.toInspectionStateEntity
import com.example.servicemanager.feature_app_data.mappers.toRepairStateEntity
import com.example.servicemanager.feature_app_data.mappers.toTechnicianEntity
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
import kotlinx.coroutines.flow.*

class  AppRepositoryImplementation(
    val appDatabaseDao: AppDatabaseDao,
    val firebaseApi: AppFirebaseApi
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
                "Locally cached list"
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
                    "Device list fetching finished"
                )
            )
        }
    }

    /* ********************************* TECHNICIANS ******************************************** */
    override fun getTechnicianList() = flow {
        var technicianList: List<Technician>
        technicianList = appDatabaseDao.getTechnicianList().map { it.toTechnician() }
        emit(
            Resource(
                ResourceState.LOADING,
                technicianList,
                "Locally cached list"
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
                    "Device list fetching finished"
                )
            )
        }
    }

    /* ********************************* INSPECTION STATES ************************************** */
    override fun getInspectionStateList() = flow<Resource<List<InspectionState>>> {
        var inspectionStateList: List<InspectionState>
        inspectionStateList = appDatabaseDao.getInspectionStateList().map { it.toInspectionState() }
        emit(
            Resource(
                ResourceState.LOADING,
                inspectionStateList,
                "Locally cached list"
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
                    "Inspection state list fetching finished"
                )
            )
        }
    }

    /* ********************************* REPAIR STATES ****************************************** */
    override fun getRepairStateList() = flow<Resource<List<RepairState>>> {
        var repairStateList: List<RepairState>
        repairStateList = appDatabaseDao.getRepairStateList().map { it.toRepairState() }
        emit(
            Resource(
                ResourceState.LOADING,
                repairStateList,
                "Locally cached list"
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
                    "Inspection state list fetching finished"
                )
            )
        }
    }

    /* ********************************* EST STATES ********************************************* */
    override fun getEstStateList() = flow<Resource<List<EstState>>> {
        var estStateList: List<EstState>
        estStateList = appDatabaseDao.getEstStateList().map { it.toEstState() }
        emit(
            Resource(
                ResourceState.LOADING,
                estStateList,
                "Locally cached list"
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
                    "Inspection state list fetching finished"
                )
            )
        }
    }

    /* ********************************* USER *************************************************** */
    override fun getUser(userId: String): Flow<Resource<User>> = flow {
        var user = firebaseApi.getUser(userId)
        if (user != null){
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    user,
                    "User fetching finished"
                )
            )
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    user,
                    "User fetching error"
                )
            )
        }
    }

}
