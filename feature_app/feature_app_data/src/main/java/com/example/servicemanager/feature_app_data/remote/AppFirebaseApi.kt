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
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.jvm.Throws


// Repository class

class  AppFirebaseApi(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseStorage: FirebaseStorage,
) {

    private val APP_FIREBASE_API = "APP_FIREBASE_API"


    /* ********************************* SIGNATURES ********************************************* */
    suspend fun uploadSignature(signatureId: String, signatureBytes: ByteArray): Resource<String>  {
        try {
            // TODO Caching mechanism in uploadSignature fun for AppFirebaseApi implementation
            Log.d(APP_FIREBASE_API, "Signature uploading started")
            val documentReference = firebaseStorage.getReference("signatures")
                .child("${signatureId}.jpg")
            val result = documentReference.putBytes(signatureBytes)
            result.await()
            if (result.isSuccessful) {
                Log.d(APP_FIREBASE_API, "Signature uploaded")
                return Resource(
                    ResourceState.SUCCESS,
                    documentReference.downloadUrl.toString(),
                    UiText.StringResource(R.string.upload_signature_success)
                )

            } else {
                Log.d(APP_FIREBASE_API, "Signature uploading error")
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.unknown_error)
                )
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d(APP_FIREBASE_API, "Signature uploading error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }

    }
    suspend fun getSignature(signatureId: String): Resource<ByteArray> {
        if (signatureId.isEmpty()) {
            throw IllegalArgumentException("signatureId can not be empty")
        }
        Log.d(APP_FIREBASE_API, "Signature fetching started")
        val documentReference = firebaseStorage.getReference("signatures")
            .child("${signatureId}.jpg")
        val result = documentReference.getBytes(10000000) //  10MB
        result.await()
        return if (result.isSuccessful){
            Log.d(APP_FIREBASE_API, "Signature fetched")
            val data =  result.result
            Resource(
                ResourceState.SUCCESS,
                data
            )
        } else {
            Log.d(APP_FIREBASE_API, "Signature fetching error")
            Resource(
                    ResourceState.ERROR,
                    null
                )
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
    suspend fun updateHospital(hospital: Hospital): Resource<String> {

            val map: Map<String, String> = mapOf(
                "hospitalId" to hospital.hospitalId,
                "hospital" to hospital.hospital
            )
            val documentReference = firebaseFirestore.collection("hospitals").document(hospital.hospitalId)
            val result = documentReference.update(map)
            result.await()
            if (result.isSuccessful) {
                Log.d(APP_FIREBASE_API, "Hospital record update success")
                return Resource(
                        ResourceState.SUCCESS,
                    null,
                        UiText.StringResource(R.string.hospital_update_success)
                    )

            } else {
                Log.d(APP_FIREBASE_API, "Hospital record update error")
                return Resource(
                        ResourceState.ERROR,
                    null,
                        UiText.StringResource(R.string.hospital_update_error)
                    )
            }
    }
    suspend fun createHospital(hospital: Hospital): Resource<String> {
        val documentReference = firebaseFirestore.collection("hospitals").document()
        val map: Map<String, String> = mapOf(
            "hospitalId" to documentReference.id,
            "hospital" to hospital.hospital
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "Hospital record create success")
            return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.hospital_create_error)
                )
        } else {
            Log.d(APP_FIREBASE_API, "Hospital record create error")
            return  Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.hospital_create_error
                )
            )
        }
    }
    suspend fun createHospitalWithId(hospital: Hospital): Resource<String> {
        val documentReference = firebaseFirestore.collection("hospitals").document(hospital.hospitalId)
        val map: Map<String, String> = mapOf(
            "hospitalId" to hospital.hospitalId,
            "hospital" to hospital.hospital
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "Hospital record create success")
            return Resource(
                    ResourceState.SUCCESS,
                    documentReference.id,
                    UiText.StringResource(R.string.hospital_create_error)
                )
        } else {
            Log.d(APP_FIREBASE_API, "Hospital record create error")

            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.hospital_create_error
                    )
                )
        }
    }
    suspend fun deleteHospital(hospitalId: String): Resource<String> {
        val documentReference = firebaseFirestore.collection("hospitals").document(hospitalId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "Hospital delete success")
                return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.hospital_delete_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "Hospital delete error")
            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.hospital_delete_error)
                )
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
    suspend fun updateTechnician(technician: Technician): Resource<String> {
        val map: Map<String, String> = mapOf(
            "technicianId" to technician.technicianId,
            "name" to technician.name
        )
        val documentReference = firebaseFirestore.collection("technicians").document(technician.technicianId)
        val result = documentReference.update(map)
        result.await()
        return if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "Technician record update success")
            Resource(
                ResourceState.SUCCESS,
                null,
                UiText.StringResource(R.string.technician_update_success)
            )
        } else {
            Log.d(APP_FIREBASE_API, "Technician record update error")
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.technician_update_error)
            )
        }
    }
    suspend fun createTechnician(technician: Technician): Resource<String> {
        val documentReference = firebaseFirestore.collection("technicians").document()
        val map: Map<String, String> = mapOf(
            "technicianId" to documentReference.id,
            "name" to technician.name
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "Technician record create success")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.technician_create_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "Technician record create error")

            return Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.technician_create_error)
                )
        }
    }

    suspend fun createTechnicianWithId(technician: Technician): Resource<String> {
        val documentReference = firebaseFirestore.collection("technicians").document(technician.technicianId)
        val map: Map<String, String> = mapOf(
            "technicianId" to technician.technicianId,
            "name" to technician.name
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "Technician record create success")
            return Resource(
                ResourceState.SUCCESS,
                documentReference.id,
                UiText.StringResource(R.string.technician_create_success)
            )
        } else {
            Log.d(APP_FIREBASE_API, "Technician record create error")

            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.technician_create_error)
            )
        }
    }
    suspend fun deleteTechnician(technicianId: String): Resource<String> {
        try {
            val documentReference = firebaseFirestore.collection("technicians").document(technicianId)
            val result = documentReference.delete()
            result.await()
            return if (result.isSuccessful) {
                Log.d(APP_FIREBASE_API, "Technician record delete success")
                Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.technician_delete_success)
                )
            } else {
                Log.d(APP_FIREBASE_API, "Technician record delete error")
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            }
        } catch (e: FirebaseFirestoreException) {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.check_internet_connection)
            )
        }

    }

    /* ********************************* EST STATES ********************************************* */
    suspend fun getEstStateList(): List<EstState> {
        var estStateList = emptyList<EstState>()
        Log.d(APP_FIREBASE_API, "Est states list fetching started")
        val documentReference = firebaseFirestore.collection("est_states")
        val data = documentReference.get()
        data.await()
        if(data.isSuccessful) {
            estStateList = data.result.toObjects(EstState::class.java)
            Log.d(APP_FIREBASE_API, "Est states list fetched")
        } else {
            Log.d(APP_FIREBASE_API, "Est states list fetching error")
        }
        return estStateList
    }
    suspend fun updateEstState(estState: EstState): Resource<String> {
        val map: Map<String, String> = mapOf(
            "estStateId" to estState.estStateId,
            "estState" to estState.estState
        )
        val documentReference = firebaseFirestore.collection("est_states").document(estState.estStateId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "EstState record update success")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.eststate_update_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "EstState record update error")
            return Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.eststate_update_error)
                )
        }
    }
    suspend fun createEstState(estState: EstState): Resource<String> {
        val documentReference = firebaseFirestore.collection("est_states").document()
        val map: Map<String, String> = mapOf(
            "estStateId" to documentReference.id,
            "estState" to estState.estState
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "EstState record create success")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.eststate_create_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "EstState record create error")
            return Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.eststate_create_error)
                )
        }
    }
    suspend fun createEstStateWithId(estState: EstState): Resource<String> {
        val documentReference = firebaseFirestore.collection("est_states").document(estState.estStateId)
        val map: Map<String, String> = mapOf(
            "estStateId" to estState.estStateId,
            "estState" to estState.estState
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "EstState record create success")
            return Resource(
                ResourceState.SUCCESS,
                null,
                UiText.StringResource(R.string.eststate_create_success)
            )
        } else {
            Log.d(APP_FIREBASE_API, "EstState record create error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.eststate_create_error)
            )
        }
    }

    suspend fun deleteEstState(estStateId: String): Resource<String> {
        val documentReference = firebaseFirestore.collection("est_states").document(estStateId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "EstState record delete success")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.eststate_delete_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "EstState record delete error")
            return Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.eststate_delete_error)
                )
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
    suspend fun updateRepairState(repairState: RepairState): Resource<String> {
        val map: Map<String, String> = mapOf(
            "repairStateId" to repairState.repairStateId,
            "repairState" to repairState.repairState
        )
        val documentReference = firebaseFirestore.collection("repair_states").document(repairState.repairStateId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "RepairState record update success")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.repairstate_update_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "RepairState record update error")
            return Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.repairstate_update_error)
                )
        }
    }
    suspend fun createRepairState(repairState: RepairState): Resource<String> {
        val documentReference = firebaseFirestore.collection("repair_states").document()
        val map: Map<String, String> = mapOf(
            "repairStateId" to documentReference.id,
            "repairState" to repairState.repairState
        )

        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "RepairState record created successfully")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.repairstate_create_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "RepairState record create error")
            return Resource(
                    ResourceState.ERROR,
                null,
                    UiText.StringResource(R.string.repairstate_create_error)
                )
        }
    }
    suspend fun createRepairStateWithId(repairState: RepairState): Resource<String> {
        val documentReference = firebaseFirestore.collection("repair_states").document(repairState.repairStateId)
        val map: Map<String, String> = mapOf(
            "repairStateId" to repairState.repairStateId,
            "repairState" to repairState.repairState
        )

        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "RepairState record with Id created successfully")
            return Resource(
                ResourceState.SUCCESS,
                null,
                UiText.StringResource(R.string.repairstate_create_success)
            )
        } else {
            Log.d(APP_FIREBASE_API, "RepairState record create error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repairstate_create_error)
            )
        }
    }

    suspend fun deleteRepairState(repairStateId: String): Resource<String> {
        val documentReference = firebaseFirestore.collection("repair_states").document(repairStateId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "RepairState record delete success")
            return Resource(
                    ResourceState.SUCCESS,
                null,
                    UiText.StringResource(R.string.repairstate_delete_success)
            )
        } else {
            Log.d(APP_FIREBASE_API, "RepairState record delete error")
            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.repairstate_delete_error)
                )
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
    suspend fun updateInspectionState(inspectionState: InspectionState): Resource<String> {
        val map: Map<String, String> = mapOf(
            "inspectionStateId" to inspectionState.inspectionStateId,
            "inspectionState" to inspectionState.inspectionState
        )
        val documentReference = firebaseFirestore.collection("inspection_states").document(inspectionState.inspectionStateId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "InspectionState record update success")
            return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.inspectionstate_update_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "InspectionState record update error")
            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.inspectionstate_update_error)
                )
        }
    }
    suspend fun createInspectionState(inspectionState: InspectionState): Resource<String> {
        val documentReference = firebaseFirestore.collection("inspection_states").document()
        val map: Map<String, String> = mapOf(
            "inspectionStateId" to documentReference.id,
            "inspectionState" to inspectionState.inspectionState
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "InspectionState record create success")
            return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.inspectionstate_create_success)
                )

        } else {
            Log.d(APP_FIREBASE_API, "InspectionState record create error")
            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.inspectionstate_create_error)
                )
        }
    }
    suspend fun createInspectionStateWithId(inspectionState: InspectionState): Resource<String> {
        val documentReference = firebaseFirestore.collection("inspection_states").document(inspectionState.inspectionStateId)
        val map: Map<String, String> = mapOf(
            "inspectionStateId" to inspectionState.inspectionStateId,
            "inspectionState" to inspectionState.inspectionState
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "InspectionState record create success")
            return Resource(
                ResourceState.SUCCESS,
                null,
                UiText.StringResource(R.string.inspectionstate_create_success)
            )

        } else {
            Log.d(APP_FIREBASE_API, "InspectionState record create error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.inspectionstate_create_error)
            )
        }
    }

    suspend fun deleteInspectionState(inspectionStateId: String): Resource<String> {
        val documentReference = firebaseFirestore.collection("inspection_states").document(inspectionStateId)
        val result = documentReference.delete()
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "InspectionState record delete success")
            return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.inspectionstate_delete_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "InspectionState record delete error")
            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.inspectionstate_delete_error)
                )
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

    suspend fun updateUserType(userType: UserType): Resource<String> {
        val hospitals = userType.hospitals.toString()
        val map: Map<String, String> = mapOf(
            "userTypeId" to userType.userTypeId,
            "userTypeName" to userType.userTypeName,
            "hospitals" to hospitals // Can be like this?
            // TODO How to pass list of hospitals in here
        )
        val documentReference = firebaseFirestore.collection("user_types").document(userType.userTypeId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(APP_FIREBASE_API, "UserType record update success")
            return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.usertype_update_success)
                )
        } else {
            Log.d(APP_FIREBASE_API, "UserType record update error")
            return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.usertype_update_error)
                )
        }
    }
    suspend fun createUserType(userType: UserType): Resource<String> {
        try {
            val documentReference = firebaseFirestore.collection("user_types").document(userType.userTypeId)
            val map: Map<String, String> = mapOf(
                "userTypeId" to documentReference.id,
                "userTypeName" to userType.userTypeName,
                "hospitals" to userType.hospitals.toString()
                // TODO How to pass list of hospitals in here
            )
            val result = documentReference.set(map)
            result.await()
            if (result.isSuccessful) {
                Log.d(APP_FIREBASE_API, "UserType record create success")
                return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.usertype_create_success)
                )
            } else {
                Log.d(APP_FIREBASE_API, "UserType record create error")
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.usertype_create_error)
                )
            }
        } catch (e: FirebaseFirestoreException) {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.check_internet_connection)
            )
        }

    }
    suspend fun deleteUserType(userTypeId: String): Resource<String> {
        try {
            val documentReference = firebaseFirestore.collection("user_types").document(userTypeId)
            val result = documentReference.delete()
            result.await()
            if (result.isSuccessful) {
                Log.d(APP_FIREBASE_API, "UserType record delete success")
                return Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.usertype_delete_success)
                )
            } else {
                Log.d(APP_FIREBASE_API, "UserType record delete error")
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d(APP_FIREBASE_API, "UserType record delete error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.check_internet_connection)
            )
        }

    }


}