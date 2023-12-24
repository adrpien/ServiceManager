package com.example.logger

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.core.util.MapperExtensionFunction.toMap
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseLogger(
    val firebaseAnalytics: FirebaseAnalytics
): AppLogger
{

    override fun logEvent(eventLogType: EventLogType, message: String, params: Bundle) {
        params.putString("message", message)
        firebaseAnalytics.logEvent(eventLogType.logId, params)
    }


    override fun logInspection(eventLogType: EventLogType, message: String, inspection: Inspection) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = inspection.toMap()
        map.forEach { key, value ->
            bundle.putString( key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

    override fun logRepair(eventLogType: EventLogType, message: String, repair: Repair){
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = repair.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

    override fun logHospital(eventLogType: EventLogType, message: String, hospital: Hospital) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = hospital.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)    }

    override fun logEstState(eventLogType: EventLogType, message: String, estState: EstState) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = estState.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)    }

    override fun logRepairState(
        eventLogType: EventLogType,
        message: String,
        repairState: RepairState,
    ) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = repairState.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

    override fun logInspectionState(
        eventLogType: EventLogType,
        message: String,
        inspectionState: InspectionState,
    ) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = inspectionState.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

    override fun logUserType(
        eventLogType: EventLogType,
        message: String,
        userType: UserType,
    ) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = userType.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

    override fun logTechnician(
        eventLogType: EventLogType,
        message: String,
        technician: Technician,
    ) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = technician.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }
}