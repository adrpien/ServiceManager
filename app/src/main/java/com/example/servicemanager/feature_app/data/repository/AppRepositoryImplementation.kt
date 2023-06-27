package com.example.servicemanager.feature_app.data.repository

import android.util.Log
import com.example.servicemanager.feature_app.data.local.AppDatabaseDao
import com.example.servicemanager.feature_app.data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import kotlinx.coroutines.flow.*

class  AppRepositoryImplementation(
    val appDao: AppDatabaseDao,
    val firebaseApi: AppFirebaseApi
): AppRepository {

    private val APP_REPOSITORY_IMPLEMENTATION = "TIEMED_REPOSITORY_IMPLEMENTATION"


    /* ********************************* DEVICES ************************************************ */
    override fun getDeviceList() = flow<Resource<List<Device>>> {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Device list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Device list fetching started"))
        var deviceList: List<Device> = appDao.getDeviceList().map { it.toDevice() }
        emit(Resource(ResourceState.LOADING, deviceList, "Locally cached list"))
        val list = firebaseApi.getDeviceList()?: emptyList()
        if(list.isNotEmpty()) {
            appDao.deleteAllDevices()
            for (device in list) {
                appDao.insertDevice(device.toDeviceEntity())
            }
            deviceList = appDao.getDeviceList().map { it.toDevice() }
            emit(Resource(ResourceState.SUCCESS, deviceList, "Device list fetching finished"))
        }
    }

    override fun getDevice(deviceId: String): Flow<Resource<Device>> = flow {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Device record fetching started")
        emit(Resource(ResourceState.LOADING, null, "Device record fetching started"))
        var device = appDao.getDevice(deviceId).toDevice()
        emit(Resource(ResourceState.LOADING, device, "Locally cached record"))
        val record = firebaseApi.getDevice(deviceId)
        if(record != null) {
            appDao.deleteDevice(deviceId)
            appDao.insertDevice(record.toDeviceEntity())
            device = appDao.getDevice(deviceId).toDevice()
            emit(Resource(ResourceState.SUCCESS, device, "Device record fetching finished"))
        }
    }
    override fun insertDevice(device: Device): Flow<Resource<Boolean>> {
        return firebaseApi.createDevice(device)
    }
    override fun updateDevice(device: Device): Flow<Resource<Boolean>> {
        return firebaseApi.updateDevice(device)
    }


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

    override fun getHospitalList() = flow<Resource<List<Hospital>>> {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Hospital list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Hospital list fetching started"))
        var hospitalList: List<Hospital> = appDao.getHospitalList().map { it.toHospital() }
        emit(Resource(ResourceState.LOADING, hospitalList, "Locally cached list"))
        val list = firebaseApi.getHospitalList()?: emptyList()
        if(list.isNotEmpty()) {
            appDao.deleteAllHospitals()
            for (hospital in list) {
                appDao.insertHospital(hospital.toHospitalEntity())
            }
            hospitalList = appDao.getHospitalList().map { it.toHospital() }
            emit(Resource(ResourceState.SUCCESS, hospitalList, "Device list fetching finished"))
        }
    }

    /* ********************************* TECHNICIANS ******************************************** */

    override fun getTechnicianList() = flow {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Technician list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Technician list fetching started"))
        var technicianList: List<Technician> = appDao.getTechnicianList().map { it.toTechnician() }
        emit(Resource(ResourceState.LOADING, technicianList, "Locally cached list"))
        val list = firebaseApi.getTechnicianList()?: emptyList()
        if(list.isNotEmpty()) {
            appDao.deleteAllTechnicians()
            for (technician in list) {
                appDao.insertTechnician(technician.toTechnicianEntity())
            }
            technicianList = appDao.getTechnicianList().map { it.toTechnician() }
            emit(Resource(ResourceState.SUCCESS, technicianList, "Device list fetching finished"))
        }
    }

    /* ********************************* INSPECTION STATES ************************************** */
    override fun getInspectionStateList() = flow<Resource<List<InspectionState>>> {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Inspection state list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Inspection state list fetching started"))
        var inspectionStateList: List<InspectionState> = appDao.getInspectionStateList().map { it.toInspectionState() }
        emit(Resource(ResourceState.LOADING, inspectionStateList, "Locally cached list"))
        val list = firebaseApi.getInspectionStateList()?: emptyList()
        if(list.isNotEmpty()) {
            appDao.deleteAllInspectionStates()
            for (inspectionState in list) {
                appDao.insertInspectionState(inspectionState.toInspectionStateEntity())
            }
            inspectionStateList = appDao.getInspectionStateList().map { it.toInspectionState() }
            emit(Resource(ResourceState.SUCCESS, inspectionStateList, "Inspection state list fetching finished"))
        }
    }

    /* ********************************* REPAIR STATES ****************************************** */
    override fun getRepairStateList() = flow<Resource<List<RepairState>>> {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Repair state list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Repair state list fetching started"))
        var repairStateList: List<RepairState> = appDao.getRepairStateList().map { it.toRepairState() }
        emit(Resource(ResourceState.LOADING, repairStateList, "Locally cached list"))
        val list = firebaseApi.getRepairStateList()?: emptyList()
        if(list.isNotEmpty()) {
            appDao.deleteAllRepairStates()
            for (repairState in list) {
                appDao.insertRepairState(repairState.toRepairStateEntity())
            }
            repairStateList = appDao.getRepairStateList().map { it.toRepairState() }
            emit(Resource(ResourceState.SUCCESS, repairStateList, "Inspection state list fetching finished"))
        }
    }

    /* ********************************* EST STATES ********************************************* */
    override fun getEstStateList() = flow<Resource<List<EstState>>> {
        Log.d(APP_REPOSITORY_IMPLEMENTATION, "Est state list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Est state list fetching started"))
        var estStateList: List<EstState> = appDao.getEstStateList().map { it.toEstState() }
        emit(Resource(ResourceState.LOADING, estStateList, "Locally cached list"))
        val list = firebaseApi.getEstStateList()?: emptyList()
        if(list.isNotEmpty()) {
            appDao.deleteAllEstStates()
            for (estState in list) {
                appDao.insertEstState(estState.toEstStateEntity())
            }
            estStateList = appDao.getEstStateList().map { it.toEstState() }
            emit(Resource(ResourceState.SUCCESS, estStateList, "Inspection state list fetching finished"))
        }
    }
}
