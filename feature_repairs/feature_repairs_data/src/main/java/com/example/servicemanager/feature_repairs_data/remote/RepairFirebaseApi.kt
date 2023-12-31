package com.example.servicemanager.feature_repairs_data.remote

import android.util.Log
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_data.R
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class  RepairFirebaseApi(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseStorage: FirebaseStorage
) {

    private val REPAIR_REPOSITORY_API = "REPAIR_REPOSITORY_API"

    /* ********************************* REPAIRS ********************************* */
    suspend fun getRepairList(): List<Repair> {
        Log.d(REPAIR_REPOSITORY_API, "Repair list fetching started")
        var repairList: List<Repair> = emptyList()
        val documentReference = firebaseFirestore.collection("repairs")
        val result = documentReference.get()
        result.await()
        if (result.isSuccessful) {
            repairList =  result.result.toObjects(Repair::class.java)
            Log.d(REPAIR_REPOSITORY_API, "Repair list fetched")

        } else {
            Log.d(REPAIR_REPOSITORY_API, "Repair list fetch error")
        }
        return repairList
    }
    suspend fun getRepair(repairId: String): Repair? {
        Log.d(REPAIR_REPOSITORY_API, "Repair record fetching started")
        var repair: Repair? = Repair()
        val documentReference = firebaseFirestore.collection("repairs")
            .document(repairId)
            val result = documentReference.get()
            result.await()
            if (result.isSuccessful) {
                repair =  result.result.toObject(Repair::class.java)
                Log.d(REPAIR_REPOSITORY_API, "Repair list fetched")

            } else {
                Log.d(REPAIR_REPOSITORY_API, "Repair list fetch error")
            }
        return repair
    }
    suspend fun createRepair(repair: Repair): Resource<String> {
        // TODO Caching mechanism in createRepair fun for RepairFirebaseApi
        var documentReference = firebaseFirestore.collection("repairs")
            .document()
        var map = mapOf<String, String>(
            "repairId" to documentReference.id,
            "repairStateId" to repair.repairStateId,
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
            "deviceName" to repair.deviceName,
            "deviceManufacturer" to repair.deviceManufacturer,
            "deviceModel" to repair.deviceModel,
            "deviceSn" to repair.deviceSn,
            "deviceIn" to repair.deviceIn
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(REPAIR_REPOSITORY_API, "Repair record created")
            return Resource(
                ResourceState.SUCCESS,
                documentReference.id,
                UiText.StringResource(R.string.repair_record_created)
            )

        } else {
            Log.d(REPAIR_REPOSITORY_API, "Repair record creation error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repair_record_creation_error)
            )
        }
    }
    suspend fun updateRepair(repair: Repair): Resource<String> {
        // TODO Caching mechanism in updateRepair fun for RepairFirebaseApi
        var map = mapOf<String, String>(
            "repairId" to repair.repairId,
            "repairStateId" to repair.repairStateId,
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
            "deviceName" to repair.deviceName,
            "deviceManufacturer" to repair.deviceManufacturer,
            "deviceModel" to repair.deviceModel,
            "deviceSn" to repair.deviceSn,
            "deviceIn" to repair.deviceIn
        )
        val documentReference = firebaseFirestore.collection("repairs").document(repair.repairId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            Log.d(REPAIR_REPOSITORY_API, "Repair record updated")
            return Resource(
                ResourceState.SUCCESS,
                documentReference.id,
                UiText.StringResource(R.string.repair_record_updated)
            )
        } else {
            Log.d(REPAIR_REPOSITORY_API, "Repair record update error")
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repair_record_update_error)
            )
        }

    }

    }