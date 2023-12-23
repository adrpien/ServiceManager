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
                ResourceState.LOADING,
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
                                ResourceState.SUCCESS,
                                documentReference.downloadUrl.toString()
                            )
                        )
                        Log.d(APP_FIREBASE_API, "Signature uploaded")

                    } else {
                        emit(
                            Resource(
                                ResourceState.ERROR,
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
                ResourceState.LOADING,
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
                                ResourceState.SUCCESS,
                                data
                            )
                        )
                        Log.d(APP_FIREBASE_API, "Signature fetched")
                    } else {
                        emit(
                            Resource(
                                ResourceState.ERROR,
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
                "Update hospital started",
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
                        "Hospital update success",
                        UiText.StringResource(R.string.hospital_update_success)
                    )
                )
                Log.d(APP_FIREBASE_API, "Hospital record update success")
            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Hospital update error",
                        UiText.StringResource(R.string.hospital_update_error)
                    )
                )
                Log.d(APP_FIREBASE_API, "Hospital record update error")
            }
    }
    fun createHospital(hospital: Hospital): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Create hospital started",
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
                    UiText.StringResource(R.string.hospital_create_error)
                )
            )
            Log.d(APP_FIREBASE_API, "Hospital record create success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Hospital create error",
                    UiText.StringResource(R.string.hospital_create_error
                )
            )
            )
            Log.d(APP_FIREBASE_API, "Hospital record create error")

        }
    }
    fun deleteHospital(hospitalId: String): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Delete hospital started",
                null)
        )
        val documentReference = firebaseFirestore.collection("hospitals").document(hospitalId)

        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "Hospital delete success",
                    UiText.StringResource(R.string.hospital_delete_success)
                )
            )
            Log.d(APP_FIREBASE_API, "Hospital delete success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Hospital delete error",
                    UiText.StringResource(R.string.hospital_delete_error)
                )
            )
            Log.d(APP_FIREBASE_API, "Hospital delete error")
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
    fun updateTechnician(technician: Technician): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update technician started",
                null)
        )
        val map: Map<String, String> = mapOf(
            "technicianId" to technician.technicianId,
            "name" to technician.name
        )
        val documentReference = firebaseFirestore.collection("technicians").document(technician.technicianId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "Technician update success",
                    UiText.StringResource(R.string.technician_update_success)
                )
            )
            Log.d(APP_FIREBASE_API, "Technician record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Technician update error",
                    UiText.StringResource(R.string.technician_update_error)
                )
            )
            Log.d(APP_FIREBASE_API, "Technician record update error")
        }
    }
    fun createTechnician(technician: Technician): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Create technician started",
                null)
        )
        val documentReference = firebaseFirestore.collection("technicians").document()

        val map: Map<String, String> = mapOf(
            "technicianId" to documentReference.id,
            "name" to technician.name
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "Technician create success",
                    UiText.StringResource(R.string.technician_create_success)
                )
            )
            Log.d(APP_FIREBASE_API, "Technician record create success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Technician create error",
                    UiText.StringResource(R.string.technician_create_error)
                )
            )
            Log.d(APP_FIREBASE_API, "Technician record create error")
        }
    }
    fun deleteTechnician(technicianId: String): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Delete technician started",
                null)
        )
        val documentReference = firebaseFirestore.collection("technicians").document(technicianId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "Technician delete success",
                    UiText.StringResource(R.string.technician_delete_success)
                )
            )
            Log.d(APP_FIREBASE_API, "Technician record delete success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Technician delete error",
                    UiText.StringResource(R.string.technician_delete_error)
                )
            )
            Log.d(APP_FIREBASE_API, "Technician record delete error")
        }
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
    fun updateEstState(estState: EstState): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update EstState started",
                null)
        )
        val map: Map<String, String> = mapOf(
            "estStateId" to estState.estStateId,
            "estState" to estState.estState
        )
        val documentReference = firebaseFirestore.collection("est_states").document(estState.estStateId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "EstState update success",
                    UiText.StringResource(R.string.eststate_update_success)
                )
            )
            Log.d(APP_FIREBASE_API, "EstState record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "EstState update error",
                    UiText.StringResource(R.string.eststate_update_error)
                )
            )
            Log.d(APP_FIREBASE_API, "EstState record update error")
        }
    }
    fun createEstState(estState: EstState): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Create EstState started",
                null)
        )
        val documentReference = firebaseFirestore.collection("est_states").document()
        val map: Map<String, String> = mapOf(
            "estStateId" to documentReference.id,
            "estState" to estState.estState
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "EstState create success",
                    UiText.StringResource(R.string.eststate_create_success)
                )
            )
            Log.d(APP_FIREBASE_API, "EstState record create success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "EstState create error",
                    UiText.StringResource(R.string.eststate_create_error)
                )
            )
            Log.d(APP_FIREBASE_API, "EstState record create error")
        }
    }
    fun deleteEstState(estStateId: String): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Delete EstState started",
                null)
        )
        val documentReference = firebaseFirestore.collection("est_state").document(estStateId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "EstState delete success",
                    UiText.StringResource(R.string.eststate_delete_success)
                )
            )
            Log.d(APP_FIREBASE_API, "EstState record delete success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "EstState delete error",
                    UiText.StringResource(R.string.eststate_delete_error)
                )
            )
            Log.d(APP_FIREBASE_API, "EstState record delete error")
        }
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
    fun updateRepairState(repairState: RepairState): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update RepairState started",
                null)
        )
        val map: Map<String, String> = mapOf(
            "repairStateId" to repairState.repairStateId,
            "repairState" to repairState.repairState
        )
        val documentReference = firebaseFirestore.collection("repair_states").document(repairState.repairStateId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "RepairState update success",
                    UiText.StringResource(R.string.repairstate_update_success)
                )
            )
            Log.d(APP_FIREBASE_API, "RepairState record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "RepairState update error",
                    UiText.StringResource(R.string.repairstate_update_error)
                )
            )
            Log.d(APP_FIREBASE_API, "RepairState record update error")
        }
    }
    fun createRepairState(repairState: RepairState): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Create RepairState started",
                null)
        )
        val documentReference = firebaseFirestore.collection("repair_states").document()
        val map: Map<String, String> = mapOf(
            "repairStateId" to documentReference.id,
            "repairState" to repairState.repairState
        )

        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "RepairState create success",
                    UiText.StringResource(R.string.repairstate_create_success)
                )
            )
            Log.d(APP_FIREBASE_API, "RepairState record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "RepairState update error",
                    UiText.StringResource(R.string.repairstate_create_error)
                )
            )
            Log.d(APP_FIREBASE_API, "RepairState record create error")
        }
    }
    fun deleteRepairState(repairStateId: String): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Delete RepairState started",
                null)
        )
        val documentReference = firebaseFirestore.collection("repair_states").document(repairStateId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "RepairState delete success",
                    UiText.StringResource(R.string.repairstate_delete_success)
                )
            )
            Log.d(APP_FIREBASE_API, "RepairState record delete success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "RepairState delete error",
                    UiText.StringResource(R.string.repairstate_delete_error)
                )
            )
            Log.d(APP_FIREBASE_API, "RepairState record delete error")
        }
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
    fun updateInspectionState(inspectionState: InspectionState): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update InspectionState started",
                null)
        )
        val map: Map<String, String> = mapOf(
            "inspectionStateId" to inspectionState.inspectionStateId,
            "inspectionState" to inspectionState.inspectionState
        )
        val documentReference = firebaseFirestore.collection("inspection_states").document(inspectionState.inspectionStateId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "InspectionState update success",
                    UiText.StringResource(R.string.inspectionstate_update_success)
                )
            )
            Log.d(APP_FIREBASE_API, "InspectionState record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "InspectionState update error",
                    UiText.StringResource(R.string.inspectionstate_update_error)
                )
            )
            Log.d(APP_FIREBASE_API, "InspectionState record update error")
        }
    }
    fun createInspectionState(inspectionState: InspectionState): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update InspectionState started",
                null)
        )
        val documentReference = firebaseFirestore.collection("inspection_states").document()
        val map: Map<String, String> = mapOf(
            "inspectionStateId" to documentReference.id,
            "inspectionState" to inspectionState.inspectionState
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "InspectionState create success",
                    UiText.StringResource(R.string.inspectionstate_create_success)
                )
            )
            Log.d(APP_FIREBASE_API, "InspectionState record create success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "InspectionState create error",
                    UiText.StringResource(R.string.inspectionstate_create_error)
                )
            )
            Log.d(APP_FIREBASE_API, "InspectionState record create error")
        }
    }
    fun deleteInspectionState(inspectionStateId: String): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Delete InspectionState started",
                null)
        )
        val documentReference = firebaseFirestore.collection("inspection_states").document(inspectionStateId)

        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "InspectionState delete success",
                    UiText.StringResource(R.string.inspectionstate_delete_success)
                )
            )
            Log.d(APP_FIREBASE_API, "InspectionState record delete success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "InspectionState delete error",
                    UiText.StringResource(R.string.inspectionstate_delete_error)
                )
            )
            Log.d(APP_FIREBASE_API, "InspectionState record delete error")
        }
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

    fun updateUserType(userType: UserType): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update UserType started",
                null)
        )
        val map: Map<String, String> = mapOf(
            "userTypeId" to userType.userTypeId,
            "userTypeName" to userType.userTypeName,
            // TODO How to pass list of hospitals in here
        )
        val documentReference = firebaseFirestore.collection("user_types").document(userType.userTypeId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "UserType update success",
                    UiText.StringResource(R.string.usertype_update_success)
                )
            )
            Log.d(APP_FIREBASE_API, "UserType record update success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "UserType update error",
                    UiText.StringResource(R.string.usertype_update_error)
                )
            )
            Log.d(APP_FIREBASE_API, "UserType record update error")
        }
    }
    fun createUserType(userType: UserType): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Create UserType started",
                null)
        )
        val documentReference = firebaseFirestore.collection("user_types").document(userType.userTypeId)
        val map: Map<String, String> = mapOf(
            "userTypeId" to documentReference.id,
            "userTypeName" to userType.userTypeName,
            // TODO How to pass list of hospitals in here
        )

        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "UserType create success",
                    UiText.StringResource(R.string.usertype_create_success)
                )
            )
            Log.d(APP_FIREBASE_API, "UserType record create success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "UserType update error",
                    UiText.StringResource(R.string.usertype_create_error)
                )
            )
            Log.d(APP_FIREBASE_API, "UserType record create error")
        }
    }
    fun deleteUserType(userTypeId: String): Flow<Resource<String>> = flow {
        emit(
            Resource(
                ResourceState.LOADING,
                "Update UserType started",
                null)
        )
        val documentReference = firebaseFirestore.collection("user_types").document(userTypeId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "UserType delete success",
                    UiText.StringResource(R.string.usertype_delete_success)
                )
            )
            Log.d(APP_FIREBASE_API, "UserType record delete success")
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "UserType delete error",
                    UiText.StringResource(R.string.usertype_delete_error)
                )
            )
            Log.d(APP_FIREBASE_API, "UserType record delete error")
        }
    }


}