package com.example.servicemanager.feature_inspections_data.remote

import android.util.Log
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_data.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class  InspectionFirebaseApi(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
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
    suspend fun createInspection(inspection: Inspection): Resource<Inspection> {
        try {
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
                Log.d(INSPECTION_FIREBASE_API, "Inspection record created")
                return Resource(
                    ResourceState.SUCCESS,
                    inspection,
                    UiText.StringResource(R.string.inspection_record_created)
                )
            } else {
                Log.d(INSPECTION_FIREBASE_API, "Error creating new inspection record")
                return Resource(
                    ResourceState.ERROR,
                    inspection,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d(INSPECTION_FIREBASE_API, "Error creating new inspection record")
            return Resource(
                ResourceState.ERROR,
                inspection,
                UiText.StringResource(R.string.check_internet_connection)
            )
        }
    }
    suspend fun updateInspection(inspection: Inspection): Resource<Inspection> {
        // TODO Caching mechanism in updateInspection fun for InspectionFirebaseApi
        try {
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
                "deviceSn" to inspection.deviceSn,
                "deviceIn" to inspection.deviceIn
            )
            val documentReference = firebaseFirestore.collection("inspections").document(inspection.inspectionId)
            val result = documentReference.update(map)
            result.await()
            if (result.isSuccessful) {
                Log.d(INSPECTION_FIREBASE_API, "Inspection record updated")
                return Resource(
                    ResourceState.SUCCESS,
                    inspection,
                    UiText.StringResource(R.string.inspection_record_updated)
                )
            } else {
                Log.d(INSPECTION_FIREBASE_API, "Inspection record update error")
                return Resource(
                    ResourceState.ERROR,
                    inspection,
                    UiText.StringResource(R.string.check_internet_connection)
                )
            }
        } catch (e: FirebaseFirestoreException) {
            return Resource(
                ResourceState.ERROR,
                inspection,
                UiText.StringResource(R.string.check_internet_connection)
            )
        }
    }

    }