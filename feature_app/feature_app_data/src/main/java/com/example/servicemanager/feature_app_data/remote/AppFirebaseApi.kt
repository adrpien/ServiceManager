package com.example.servicemanager.feature_app_data.remote

import android.util.Log
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
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


    /* ********************************* SIGNATURES ********************************************* */
    fun uploadSignature(signatureId: String, signatureBytes: ByteArray): Flow<com.example.core.util.Resource<String>> = flow {
        // TODO Caching mechanism in uploadSignature fun for AppFirebaseApi implementation
        Log.d(APP_REPOSITORY, "Signature uploading started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                null
            )
        )
        val documentReference = firebaseStorage.getReference("signatures")
            .child("${signatureId}.jpg")
            val result = documentReference.putBytes(signatureBytes)
            result.await()
                    if (result.isSuccessful) {
                        emit(
                            com.example.core.util.Resource(
                                com.example.core.util.ResourceState.SUCCESS,
                                documentReference.downloadUrl.toString()
                            )
                        )
                        Log.d(APP_REPOSITORY, "Signature uploaded")

                    } else {
                        emit(
                            com.example.core.util.Resource(
                                com.example.core.util.ResourceState.ERROR,
                                null
                            )
                        )
                        Log.d(APP_REPOSITORY, "Signature uploading error")
                    }

    }
    fun getSignature(signatureId: String): Flow<com.example.core.util.Resource<ByteArray>> = flow {
        Log.d(APP_REPOSITORY, "Signature fetching started")
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                null
            )
        )
        val documentReference = firebaseStorage.getReference("signatures")
            .child("${signatureId}.jpg")
            val result = documentReference.getBytes(10000000) //  10MB
            result.await()
                    if (result.isSuccessful){
                        val data =  result.result
                        emit(
                            com.example.core.util.Resource(
                                com.example.core.util.ResourceState.SUCCESS,
                                data
                            )
                        )
                        Log.d(APP_REPOSITORY, "Signature fetched")
                    } else {
                        emit(
                            com.example.core.util.Resource(
                                com.example.core.util.ResourceState.ERROR,
                                null
                            )
                        )
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

    /* ********************************* USER ************************************* */
    suspend fun getUser(userId: String): User? {
        var user: User? = null
        val documentReference = firebaseFirestore.collection("users").document(userId)
        val result = documentReference.get()
        result.await()
        if(result.isSuccessful) {
            user = result.result.toObject(User::class.java)
        }
        return user
    }

}