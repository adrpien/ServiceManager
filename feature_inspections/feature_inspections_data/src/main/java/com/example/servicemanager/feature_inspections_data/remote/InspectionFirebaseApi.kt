package com.example.servicemanager.feature_inspections_data.remote

import android.util.Log
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class  InspectionFirebaseApi(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseStorage: FirebaseStorage
) {

    private val INSPECTION_FIREBASE_API = "INSPECTION FIREBASE API"

    /* ********************************* INSPECTIONS ********************************* */
    suspend fun getInspectionList(): List<Inspection> {
        Log.d(INSPECTION_FIREBASE_API, "Inspection list fetching started")
        var inspectionList: List<Inspection> = emptyList()
        val documentReference = firebaseFirestore.collection("inspections")
        val result = documentReference.get()
        result.await()
        if (result.isSuccessful) {
            inspectionList =  result.result.toObjects(Inspection::class.java)
            Log.d(INSPECTION_FIREBASE_API, "Inspection list fetched")

        } else {
            Log.d(INSPECTION_FIREBASE_API, "Inspection list fetch error")
        }
        return inspectionList
    }
    suspend fun getInspection(inspectionId: String): Inspection? {
        Log.d(INSPECTION_FIREBASE_API, "Inspection record fetching started")
        var inspection: Inspection? = null
        val documentReference = firebaseFirestore.collection("inspections")
            .document(inspectionId)
            val result = documentReference.get()
            result.await()
            if (result.isSuccessful) {
                inspection =  result.result.toObject(Inspection::class.java)
                Log.d(INSPECTION_FIREBASE_API, "Inspection list fetched")

            } else {
                Log.d(INSPECTION_FIREBASE_API, "Inspection list fetch error")
            }
        return inspection
    }
    fun createInspection(inspection: Inspection): Flow<Resource<String>> = flow {
        // TODO Caching mechanism in createInspection fun for InspectionFirebaseApi
        emit(
            Resource(
                ResourceState.LOADING,
                "0",
                "Inspection record creating started"
            )
        )
        var documentReference = firebaseFirestore.collection("inspections")
            .document()
        var map = mapOf<String, String>(
            "inspectionId" to documentReference.id,
            "inspectionStateId" to inspection.inspectionStateId,
            "hospitalId" to inspection.hospitalId,
            "ward" to inspection.ward,
            "estStateId" to inspection.estStateId,
            "comment" to inspection.comment,
            "inspectionDate" to inspection.inspectionDate,
            "recipient" to inspection.recipient,
            "signatureId" to inspection.inspectionId,
            "technicianId" to inspection.technicianId,
            "deviceName" to inspection.deviceName,
            "deviceManufacturer" to inspection.deviceManufacturer,
            "deviceModel" to inspection.deviceModel,
            "deviceSn" to inspection.deviceSn,
            "deviceIn" to inspection.deviceIn
        )
        val result = documentReference.set(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    documentReference.id,
                    documentReference.id
                )
            )
            Log.d(INSPECTION_FIREBASE_API, "Inspection record created")

        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    "Inspection record creation error",
                    "Inspection record creation error"
                )
            )
            Log.d(INSPECTION_FIREBASE_API, "Inspection record creation error")

        }
    }
    fun updateInspection(inspection: Inspection): Flow<Resource<String>> = flow {
        // TODO Caching mechanism in updateInspection fun for InspectionFirebaseApi
        emit(
            Resource(
                ResourceState.LOADING,
                "",
                "Inspection record updating started"
            )
        )
        var map = mapOf<String, String>(
            "inspectionId" to inspection.inspectionId,
            "inspectionStateId" to inspection.inspectionStateId,
            "hospitalId" to inspection.hospitalId,
            "ward" to inspection.ward,
            "estStateId" to inspection.estStateId,
            "comment" to inspection.comment,
            "inspectionDate" to inspection.inspectionDate,
            "recipient" to inspection.recipient,
            "signatureId" to inspection.inspectionId,
            "technicianId" to inspection.technicianId,
            "deviceName" to inspection.deviceName,
            "deviceManufacturer" to inspection.deviceManufacturer,
            "deviceModel" to inspection.deviceModel,
            "deviceSN" to inspection.deviceSn,
            "deviceIN" to inspection.deviceIn
        )
        val documentReference = firebaseFirestore.collection("inspections").document(inspection.inspectionId)
        val result = documentReference.update(map)
        result.await()
        if (result.isSuccessful) {
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    "Inspection record updated",
                    "Inspection record updated"
                )
            )
            Log.d(INSPECTION_FIREBASE_API, "Inspection record updated")
        } else {

            emit(
                Resource(
                    ResourceState.ERROR,
                    result.exception?.message ?: "Update inspection unknown error",
                )
            )
            Log.d(INSPECTION_FIREBASE_API, "Inspection record update error")
        }

    }

    }