package com.example.servicemanager.feature_app_data.remote

import android.util.Log
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_data.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_app_domain.model.UserType
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

    private val APP_FIREBASE_API = "APP_FIREBASE_API"


    /* ********************************* SIGNATURES ********************************************* */
    fun uploadSignature(signatureId: String, signatureBytes: ByteArray): Flow<Resource<String>> = flow {
        // TODO Caching mechanism in uploadSignature fun for AppFirebaseApi implementation
        Log.d(APP_FIREBASE_API, "Signature uploading started")
        emit(
            Resource(
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
                            Resource(
                                com.example.core.util.ResourceState.SUCCESS,
                                documentReference.downloadUrl.toString()
                            )
                        )
                        Log.d(APP_FIREBASE_API, "Signature uploaded")

                    } else {
                        emit(
                            Resource(
                                com.example.core.util.ResourceState.ERROR,
                                null
                            )
                        )
                        Log.d(APP_FIREBASE_API, "Signature uploading error")
                    }

    }
    fun getSignature(signatureId: String): Flow<Resource<ByteArray>> = flow {
        Log.d(APP_FIREBASE_API, "Signature fetching started")
        emit(
            Resource(
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
                            Resource(
                                com.example.core.util.ResourceState.SUCCESS,
                                data
                            )
                        )
                        Log.d(APP_FIREBASE_API, "Signature fetched")
                    } else {
                        emit(
                            Resource(
                                com.example.core.util.ResourceState.ERROR,
                                null
                            )
                        )
                        Log.d(APP_FIREBASE_API, "Signature fetching error")
                    }
    }

    /* ********************************* HOSPITALS ********************************************** */
    suspend fun getHospitalList(): List<Hospital> {
        var hospitalList = emptyList<Hospital>()
        Log.d(APP_FIREBASE_API, "Hospital list fetching started")
        val documentReference = firebaseFirestore.collection("hospitals")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            hospitalList = data.result.toObjects(Hospital::class.java)
            Log.d(APP_FIREBASE_API, "Hospital list fetched")

        } else {
            Log.d(APP_FIREBASE_API, "Hospital list fetching error")
        }
        return hospitalList
    }
    fun updateHospital(hospital: Hospital): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update started",
                null)
        )
            val map: Map<String, String> = mapOf(
                "hospitalId" to hospital.hospitalId,
                "hospital" to hospital.hospital
            )
            val documentReference = firebaseFirestore.collection("hospitals").document(hospital.hospitalId)
            val result = documentReference.update(map)
            result.await()
            if (result.isSuccessful) {
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        "Hospital list updated",
                        UiText.StringResource(R.string.hospital_list_updated)
                    )
                )
                Log.d(APP_FIREBASE_API, "Hospital record update success")
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Hospital list update error",
                        UiText.StringResource(R.string.hospital_list_update_error)
                    )
                )
                Log.d(APP_FIREBASE_API, "Hospital record update error")
            }
    }

    fun createHospital(hospital: Hospital): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update started",
                null)
        )
        val documentReference = firebaseFirestore.collection("hospitals").document()
        val map: Map<String, String> = mapOf(
            "hospitalId" to documentReference.id,
            "hospital" to hospital.hospital
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    documentReference.id,
                    UiText.StringResource(R.string.hospital_list_updated)
                )
            )
            Log.d(APP_FIREBASE_API, "Hospital record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Hospital list update error",
                    UiText.StringResource(R.string.hospital_list_update_error)
                )
            )
            Log.d(APP_FIREBASE_API, "Hospital record update error")
        }
    }

    /* ********************************* TECHNICIANS ******************************************** */
    suspend  fun getTechnicianList(): List<Technician> {
        var technicianList = emptyList<Technician>()
        Log.d(APP_FIREBASE_API, "Technician list fetching error")
        val documentReference = firebaseFirestore.collection("technicians")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            technicianList = data.result.toObjects(Technician::class.java)
            Log.d(APP_FIREBASE_API, "Technician list fetched")

        } else {
            Log.d(APP_FIREBASE_API, "Technician list fetching error")
        }
        return technicianList
    }

    /* ********************************* EST STATES ********************************************* */
    suspend fun getEstStateList(): List<EstState> {
        var hospitalList = emptyList<EstState>()
        Log.d(APP_FIREBASE_API, "Est states list fetching started")
        val documentReference = firebaseFirestore.collection("est_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            hospitalList = data.result.toObjects(EstState::class.java)
            Log.d(APP_FIREBASE_API, "Est states list fetched")

        } else {
            Log.d(APP_FIREBASE_API, "Est states list fetching error")
        }
        return hospitalList
    }

    /* ********************************* REPAIR STATES ****************************************** */
    suspend fun getRepairStateList(): List<RepairState> {
        var repairStateList = emptyList<RepairState>()
        Log.d(APP_FIREBASE_API, "Repair states list fetching started")
        val documentReference = firebaseFirestore.collection("repair_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            repairStateList = data.result.toObjects(RepairState::class.java)
            Log.d(APP_FIREBASE_API, "Repair states list fetched")

        } else {
            Log.d(APP_FIREBASE_API, "Repair states list fetching error")
        }
        return repairStateList
    }

    /* ********************************* INSPECTIONS STATES ************************************* */
    suspend fun getInspectionStateList(): List<InspectionState> {
        var inspectionStateList = emptyList<InspectionState>()
        Log.d(APP_FIREBASE_API, "Inspection states list fetching started")
        val documentReference = firebaseFirestore.collection("inspection_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            inspectionStateList = data.result.toObjects(InspectionState::class.java)
            Log.d(APP_FIREBASE_API, "Inspection states list fetched")

        } else {
            Log.d(APP_FIREBASE_API, "Inspection states list fetching error")
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

    suspend fun getUserTypeList(): List<UserType> {
        var userTypeList = emptyList<UserType>()
        Log.d(APP_FIREBASE_API, "User types list fetching started")
        val documentReference = firebaseFirestore.collection("user_types")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            userTypeList = data.result.toObjects(UserType::class.java)
            Log.d(APP_FIREBASE_API, "User types list fetched")

        } else {
            Log.d(APP_FIREBASE_API, "User types list fetching error")
        }
        return userTypeList
    }

}