package com.example.servicemanager.feature_repairs.data.remote

import android.util.Log
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class  RepairFirebaseApi(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth,
    val firebaseStorage: FirebaseStorage
) {

    private val REPAIR_REPOSITORY = "REPAIR_REPOSITORY"

    /* ********************************* REPAIRS ********************************* */
    suspend fun getRepairList(): List<Repair> {
        Log.d(REPAIR_REPOSITORY, "Repair list fetching started")
        var repairList: List<Repair> = emptyList()
        val documentReference = firebaseFirestore.collection("repairs")
        val result = documentReference.get()
        result.await()
        if (result.isSuccessful) {
            repairList =  result.result.toObjects(Repair::class.java)
            Log.d(REPAIR_REPOSITORY, "Repair list fetched")

        } else {
            Log.d(REPAIR_REPOSITORY, "Repair list fetch error")
        }
        return repairList
    }
    suspend fun getRepair(repairId: String): Repair? {
        Log.d(REPAIR_REPOSITORY, "Repair record fetching started")
        var repair: Repair? = null
        val documentReference = firebaseFirestore.collection("repairs")
            .document(repairId)
            val result = documentReference.get()
            result.await()
            if (result.isSuccessful) {
                repair =  result.result.toObject(Repair::class.java)
                Log.d(REPAIR_REPOSITORY, "Repair list fetched")

            } else {
                Log.d(REPAIR_REPOSITORY, "Repair list fetch error")
            }
        return repair
    }
    fun createRepair(repair: Repair): Flow<Resource<Boolean>> = flow {
        // TODO Caching mechanism in createRepair fun for RepairFirebaseApi
        emit(Resource(ResourceState.LOADING, false, null))
        var documentReference = firebaseFirestore.collection("repairs")
            .document()
        var map = mapOf<String, String>(
            "repairId" to documentReference.id,
            "repairStateId" to repair.repairStateId,
            "deviceId" to repair.deviceId,
            "hospitalId" to repair.hospitalId,
            "ward" to repair.ward,
            "defectDescription" to repair.defectDescription,
            "repairDescription" to repair.repairDescription,
            "partDescription" to repair.partDescription,
            "estStateId" to repair.estStateId,
            "closingDate" to repair.closingDate,
            "openingDate" to repair.openingDate,
            "repairingDate" to repair.repairingDate,
            "pickupTechnicianId" to repair.pickupTechnicianId,
            "repairTechnicianId" to repair.repairTechnicianId,
            "returnTechnicianId" to repair.returnTechnicianId,
            "rate" to repair.rate,
            "recipient" to repair.recipient,
            "signatureId" to repair.repairId,
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(Resource(ResourceState.SUCCESS, true, documentReference.id))
            Log.d(REPAIR_REPOSITORY, "Repair record created")

        } else {
            emit(Resource(ResourceState.ERROR, false, null))
            Log.d(REPAIR_REPOSITORY, "Repair record creation error")

        }
    }
    fun updateRepair(repair: Repair): Flow<Resource<Boolean>> = flow {
        // TODO Caching mechanism in updateRepair fun for RepairFirebaseApi
        emit(Resource(ResourceState.LOADING, false))
        var map = mapOf<String, String>(
            "repairId" to repair.repairId,
            "repairStateId" to repair.repairStateId,
            "deviceId" to repair.deviceId,
            "hospitalId" to repair.hospitalId,
            "ward" to repair.ward,
            "defectDescription" to repair.defectDescription,
            "repairDescription" to repair.repairDescription,
            "partDescription" to repair.partDescription,
            "estTestId" to repair.estStateId,
            "closingDate" to repair.closingDate,
            "openingDate" to repair.openingDate,
            "repairingDate" to repair.repairingDate,
            "pickupTechnicianId" to repair.pickupTechnicianId,
            "repairTechnicianId" to repair.repairTechnicianId,
            "returnTechnicianId" to repair.returnTechnicianId,
            "rate" to repair.rate,
            "recipient" to repair.recipient,
            "signatureId" to repair.repairId,
        )
        val documentReference = firebaseFirestore.collection("repairs").document(repair.repairId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(Resource(ResourceState.SUCCESS, true))
            Log.d(REPAIR_REPOSITORY, "Repair record updated")
        } else {
            emit(Resource(ResourceState.ERROR, false))
            Log.d(REPAIR_REPOSITORY, "Repair record update error")
        }

    }

    }