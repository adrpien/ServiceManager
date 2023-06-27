package com.example.servicemanager.feature_inspections.data.remote

import android.util.Log
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class  InspectionFirebaseApi(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth,
    val firebaseStorage: FirebaseStorage
) {

    private val INSPECTION_REPOSITORY = "INSPECTION_REPOSITORY"

    /* ********************************* INSPECTIONS ********************************* */
    suspend fun getInspectionList(): List<Inspection> {
        Log.d(INSPECTION_REPOSITORY, "Inspection list fetching started")
        var inspectionList: List<Inspection> = emptyList()
        val documentReference = firebaseFirestore.collection("inspections")
        val result = documentReference.get()
        result.await()
        if (result.isSuccessful) {
            inspectionList =  result.result.toObjects(Inspection::class.java)
            Log.d(INSPECTION_REPOSITORY, "Inspection list fetched")

        } else {
            Log.d(INSPECTION_REPOSITORY, "Inspection list fetch error")
        }
        return inspectionList
    }
    suspend fun getInspection(inspectionId: String): Inspection? {
        Log.d(INSPECTION_REPOSITORY, "Inspection record fetching started")
        var inspection: Inspection? = null
        val documentReference = firebaseFirestore.collection("inspections")
            .document(inspectionId)
            val result = documentReference.get()
            result.await()
            if (result.isSuccessful) {
                inspection =  result.result.toObject(Inspection::class.java)
                Log.d(INSPECTION_REPOSITORY, "Inspection list fetched")

            } else {
                Log.d(INSPECTION_REPOSITORY, "Inspection list fetch error")
            }
        return inspection
    }
    fun createInspection(inspection: Inspection): Flow<Resource<Boolean>> = flow {
        // TODO Caching mechanism in createInspection fun for InspectionFirebaseApi
        emit(Resource(ResourceState.LOADING, false, null))
        var documentReference = firebaseFirestore.collection("inspections")
            .document()
        var map = mapOf<String, String>(
            "inspectionId" to documentReference.id,
            "inspectionStateId" to inspection.inspectionStateId,
            "deviceId" to inspection.deviceId,
            "hospitalId" to inspection.hospitalId,
            "ward" to inspection.ward,
            "estStateId" to inspection.estStateId,
            "comment" to inspection.comment,
            "inspectionDate" to inspection.inspectionDate,
            "recipient" to inspection.recipient,
            "signatureId" to inspection.recipientSignature,
            "technicianId" to inspection.technicianId
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(Resource(ResourceState.SUCCESS, true, documentReference.id))
            Log.d(INSPECTION_REPOSITORY, "Inspection record created")

        } else {
            emit(Resource(ResourceState.ERROR, false, null))
            Log.d(INSPECTION_REPOSITORY, "Inspection record creation error")

        }
    }
    fun updateInspection(inspection: Inspection): Flow<Resource<Boolean>> = flow {
        // TODO Caching mechanism in updateInspection fun for InspectionFirebaseApi
        emit(Resource(ResourceState.LOADING, false))
        var map = mapOf<String, String>(
            "inspectionId" to inspection.inspectionId,
            "inspectionStateId" to inspection.inspectionStateId,
            "deviceId" to inspection.deviceId,
            "hospitalId" to inspection.hospitalId,
            "ward" to inspection.ward,
            "estStateId" to inspection.estStateId,
            "comment" to inspection.comment,
            "inspectionDate" to inspection.inspectionDate,
            "recipient" to inspection.recipient,
            "signatureId" to inspection.recipientSignature,
            "technicianId" to inspection.technicianId
        )
        val documentReference = firebaseFirestore.collection("inspections").document(inspection.inspectionId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(Resource(ResourceState.SUCCESS, true))
            Log.d(INSPECTION_REPOSITORY, "Inspection record updated")
        } else {
            emit(Resource(ResourceState.ERROR, false))
            Log.d(INSPECTION_REPOSITORY, "Inspection record update error")
        }

    }

    }