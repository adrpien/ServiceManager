package com.example.servicemanager.feature_app.data.repository

import android.util.Log
import com.example.servicemanager.feature_app.data.local.AppDatabaseDao
import com.example.servicemanager.feature_app.data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_data.mappers.toTechnicianEntity
import kotlinx.coroutines.flow.*

class  AppRepositoryImplementation(
    val appDatabaseDao: AppDatabaseDao,
    val firebaseApi: AppFirebaseApi
): AppRepository {

    private val APP_REPOSITORY_IMPLEMENTATION = "TIEMED_REPOSITORY_IMPLEMENTATION"

    /* ********************************* SIGNATURES ********************************************* */
    override fun getSignature(signatureId: String): Flow<com.example.core.util.Resource<ByteArray>> {
        return firebaseApi.getSignature(signatureId)
    }
    override fun updateSignature(signatureId: String, byteArray: ByteArray): Flow<com.example.core.util.Resource<String>> {
        return firebaseApi.uploadSignature(signatureId, byteArray)
    }
    override fun createSignature(signatureId: String, byteArray: ByteArray): Flow<com.example.core.util.Resource<String>> {
        return firebaseApi.uploadSignature(signatureId, byteArray)
    }

    /* ********************************* HOSPITALS ********************************************* */

    override fun getHospitalList() = flow<com.example.core.util.Resource<List<Hospital>>> {
        var hospitalList = listOf<Hospital>()
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Hospital list fetching started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                hospitalList,
                "Hospital list fetching started"
            )
        )
        hospitalList = appDatabaseDao.getHospitalList().map { it.toHospital() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                hospitalList,
                "Locally cached list"
            )
        )
        val list = firebaseApi.getHospitalList()?: emptyList()
        if(list.isNotEmpty()) {
            appDatabaseDao.deleteAllHospitals()
            for (hospital in list) {
                appDatabaseDao.insertHospital(hospital.toHospitalEntity())
            }
            hospitalList = appDatabaseDao.getHospitalList().map { it.toHospital() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    hospitalList,
                    "Device list fetching finished"
                )
            )
        }
    }

    /* ********************************* TECHNICIANS ******************************************** */

    override fun getTechnicianList() = flow {
        var technicianList = listOf<Technician>()
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Technician list fetching started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                technicianList,
                "Technician list fetching started"
            )
        )
        technicianList = appDatabaseDao.getTechnicianList().map { it.toTechnician() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                technicianList,
                "Locally cached list"
            )
        )
        val list = firebaseApi.getTechnicianList()?: emptyList()
        if(list.isNotEmpty()) {
            appDatabaseDao.deleteAllTechnicians()
            for (technician in list) {
                appDatabaseDao.insertTechnician(technician.toTechnicianEntity())
            }
            technicianList = appDatabaseDao.getTechnicianList().map { it.toTechnician() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    technicianList,
                    "Device list fetching finished"
                )
            )
        }
    }

    /* ********************************* INSPECTION STATES ************************************** */
    override fun getInspectionStateList() = flow<com.example.core.util.Resource<List<InspectionState>>> {
        var inspectionStateList = listOf<InspectionState>()
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Inspection state list fetching started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                inspectionStateList,
                "Inspection state list fetching started"
            )
        )
        inspectionStateList = appDatabaseDao.getInspectionStateList().map { it.toInspectionState() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                inspectionStateList,
                "Locally cached list"
            )
        )
        val list = firebaseApi.getInspectionStateList()?: emptyList()
        if(list.isNotEmpty()) {
            appDatabaseDao.deleteAllInspectionStates()
            for (inspectionState in list) {
                appDatabaseDao.insertInspectionState(inspectionState.toInspectionStateEntity())
            }
            inspectionStateList = appDatabaseDao.getInspectionStateList().map { it.toInspectionState() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    inspectionStateList,
                    "Inspection state list fetching finished"
                )
            )
        }
    }

    /* ********************************* REPAIR STATES ****************************************** */
    override fun getRepairStateList() = flow<com.example.core.util.Resource<List<RepairState>>> {
        var repairStateList = listOf<RepairState>()
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Repair state list fetching started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                repairStateList,
                "Repair state list fetching started"
            )
        )
        repairStateList = appDatabaseDao.getRepairStateList().map { it.toRepairState() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                repairStateList,
                "Locally cached list"
            )
        )
        val list = firebaseApi.getRepairStateList()?: emptyList()
        if(list.isNotEmpty()) {
            appDatabaseDao.deleteAllRepairStates()
            for (repairState in list) {
                appDatabaseDao.insertRepairState(repairState.toRepairStateEntity())
            }
            repairStateList = appDatabaseDao.getRepairStateList().map { it.toRepairState() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    repairStateList,
                    "Inspection state list fetching finished"
                )
            )
        }
    }

    /* ********************************* EST STATES ********************************************* */
    override fun getEstStateList() = flow<com.example.core.util.Resource<List<EstState>>> {
        var estStateList = listOf<EstState>()
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Est state list fetching started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                estStateList,
                "Est state list fetching started"
            )
        )
        estStateList = appDatabaseDao.getEstStateList().map { it.toEstState() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                estStateList,
                "Locally cached list"
            )
        )
        val list = firebaseApi.getEstStateList()?: emptyList()
        if(list.isNotEmpty()) {
            appDatabaseDao.deleteAllEstStates()
            for (estState in list) {
                appDatabaseDao.insertEstState(estState.toEstStateEntity())
            }
            estStateList = appDatabaseDao.getEstStateList().map { it.toEstState() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    estStateList,
                    "Inspection state list fetching finished"
                )
            )
        }
    }
}
