package com.example.servicemanager.feature_app.data.remote

import android.util.Log
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


// Repository class

class  AppFirebaseApi(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseStorage: FirebaseStorage
) {

    private val APP_REPOSITORY = "APP_REPOSITORY"

    /* ********************************* DEVICES ************************************************ */
    suspend fun getDeviceList(): List<Device> {
        var data = emptyList<Device>()
        Log.d(APP_REPOSITORY, "Device list fetching started")
        val documentReference = firebaseFirestore.collection("devices")
        val result = documentReference.get()
        result.await()
        if (result.isSuccessful) {
            data =  result.result.toObjects(Device::class.java)
            Log.d(APP_REPOSITORY, "Device list fetched")

        } else {
            Log.d(APP_REPOSITORY, "Device list fetching error")
        }
        return data

    }
    suspend fun getDevice(deviceId: String): Device? {
        var device: Device? = null
        Log.d(APP_REPOSITORY, "Device record fetching started")
        val documentReference = firebaseFirestore.collection("devices")
            .document(deviceId)
        val result = documentReference.get()
        result.await()
        if (result.isSuccessful) {
            device =  result.result.toObject<Device>()
            Log.d(APP_REPOSITORY, "Device record fetched")

        } else {
            Log.d(APP_REPOSITORY, "Device record fetching error")
        }
        return device
    }
    fun createDevice(device: Device): Flow<Resource<Boolean>> = flow {
        // TODO Caching mechanism in createDevice fun for AppFirebaseApi implementation
        Log.d(APP_REPOSITORY, "Device record creating started")
        emit(Resource(ResourceState.LOADING, false, null))
        var documentReference = firebaseFirestore.collection("devices")
            .document()
        var map = mapOf<String, String>(
            "deviceId" to documentReference.id,
            "name" to device.name,
            "manufacturer" to device.manufacturer,
            "model" to device.model,
            "serialNumber" to device.serialNumber,
            "inventoryNumber" to device.inventoryNumber,
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(Resource(ResourceState.SUCCESS, true,  documentReference.id))
            Log.d(APP_REPOSITORY, "Device record created")

        } else {
            emit(Resource(ResourceState.ERROR, false,null))
            Log.d(APP_REPOSITORY, "Device record creating error")

        }
    }
    fun  updateDevice(device: Device): Flow<Resource<Boolean>> = flow {
        // TODO Caching mechanism in updateDevice fun for AppFirebaseApi implementation
        Log.d(APP_REPOSITORY, "Device record updating started")
        emit(Resource(ResourceState.LOADING, false))
        var map = mapOf<String, String>(
            "deviceId" to device.deviceId,
            "name" to device.name,
            "manufacturer" to device.manufacturer,
            "model" to device.model,
            "serialNumber" to device.serialNumber,
            "inventoryNumber" to device.inventoryNumber,
        )
        val documentReference = firebaseFirestore.collection("devices").document(device.deviceId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(Resource(ResourceState.SUCCESS, true))
            Log.d(APP_REPOSITORY, "Device record updated")
        } else {
            emit(Resource(ResourceState.ERROR, false))
            Log.d(APP_REPOSITORY, "Device record updating error")
        }

    }

    /* ********************************* SIGNATURES ********************************************* */
    fun uploadSignature(signatureId: String, signatureBytes: ByteArray): Flow<Resource<String>> = flow {
        // TODO Caching mechanism in uploadSignature fun for AppFirebaseApi implementation
        Log.d(APP_REPOSITORY, "Signature uploading started")
        emit(Resource(ResourceState.LOADING, null))
        val documentReference = firebaseStorage.getReference("signatures")
            .child("${signatureId}.jpg")
            val result = documentReference.putBytes(signatureBytes)
            result.await()
                    if (result.isSuccessful) {
                        emit(Resource(ResourceState.SUCCESS, documentReference.downloadUrl.toString()))
                        Log.d(APP_REPOSITORY, "Signature uploaded")

                    } else {
                        emit(Resource(ResourceState.ERROR, null))
                        Log.d(APP_REPOSITORY, "Signature uploading error")
                    }

    }
    fun getSignature(signatureId: String): Flow<Resource<ByteArray>> = flow {
        Log.d(APP_REPOSITORY, "Signature fetching started")
        emit(Resource(ResourceState.LOADING, null))
        val documentReference = firebaseStorage.getReference("signatures")
            .child("${signatureId}.jpg")
            val result = documentReference.getBytes(10000000) //  10MB
            result.await()
                    if (result.isSuccessful){
                        val data =  result.result
                        emit(Resource(ResourceState.SUCCESS, data))
                        Log.d(APP_REPOSITORY, "Signature fetched")
                    } else {
                        emit(Resource(ResourceState.LOADING, null))
                        Log.d(APP_REPOSITORY, "Signature fetching error")
                    }
    }

    /* ********************************* HOSPITALS ********************************************** */
    suspend fun getHospitalList(): List<Hospital> {
        var hospitalList = emptyList<Hospital>()
        Log.d(APP_REPOSITORY, "Hospital list fetching started")
        val documentReference = firebaseFirestore.collection("hospitals")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            hospitalList = data.result.toObjects(Hospital::class.java)
            Log.d(APP_REPOSITORY, "Hospital list fetched")

        } else {
            Log.d(APP_REPOSITORY, "Hospital list fetching error")
        }
        return hospitalList
    }

    /* ********************************* TECHNICIANS ******************************************** */
    suspend  fun getTechnicianList(): List<Technician> {
        var technicianList = emptyList<Technician>()
        Log.d(APP_REPOSITORY, "Technician list fetching error")
        val documentReference = firebaseFirestore.collection("technicians")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            technicianList = data.result.toObjects(Technician::class.java)
            Log.d(APP_REPOSITORY, "Technician list fetched")

        } else {
            Log.d(APP_REPOSITORY, "Technician list fetching error")
        }
        return technicianList
    }

    /* ********************************* EST STATES ********************************************* */
    suspend fun getEstStateList(): List<EstState> {
        var hospitalList = emptyList<EstState>()
        Log.d(APP_REPOSITORY, "Est states list fetching started")
        val documentReference = firebaseFirestore.collection("est_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            hospitalList = data.result.toObjects(EstState::class.java)
            Log.d(APP_REPOSITORY, "Est states list fetched")

        } else {
            Log.d(APP_REPOSITORY, "Est states list fetching error")
        }
        return hospitalList
    }

    /* ********************************* REPAIR STATES ****************************************** */
    suspend fun getRepairStateList(): List<RepairState> {
        var repairStateList = emptyList<RepairState>()
        Log.d(APP_REPOSITORY, "Repair states list fetching started")
        val documentReference = firebaseFirestore.collection("repair_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            repairStateList = data.result.toObjects(RepairState::class.java)
            Log.d(APP_REPOSITORY, "Repair states list fetched")

        } else {
            Log.d(APP_REPOSITORY, "Repair states list fetching error")
        }
        return repairStateList
    }

    /* ********************************* INSPECTIONS STATES ************************************* */
    suspend fun getInspectionStateList(): List<InspectionState> {
        var inspectionStateList = emptyList<InspectionState>()
        Log.d(APP_REPOSITORY, "Inspection states list fetching started")
        val documentReference = firebaseFirestore.collection("inspection_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            inspectionStateList = data.result.toObjects(InspectionState::class.java)
            Log.d(APP_REPOSITORY, "Inspection states list fetched")

        } else {
            Log.d(APP_REPOSITORY, "Inspection states list fetching error")
        }
        return inspectionStateList
    }
}