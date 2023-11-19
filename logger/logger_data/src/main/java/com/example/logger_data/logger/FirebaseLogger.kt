package com.example.logger_data.logger

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.core.util.MapperExtensionFunction
import com.example.core.util.MapperExtensionFunction.toMap
import com.example.logger_domain.logger.AppLogger
import com.example.logger_domain.util.EventLogType
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


    override fun logInspection(eventLogType: EventLogType, message: String, inspection: Inspection, userId: String) {
        val bundle = bundleOf()
        bundle.putString("message", message)
        bundle.putString("userId", userId)
        // val map = inspection.toMap()
        /*map.forEach { key, value ->
            bundle.putString( key, value.toString())
        }*/
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

    override fun logRepair(eventLogType: EventLogType, message: String, repair: Repair, userId: String){
        val bundle = bundleOf()
        val map = repair.toMap()
        map.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        bundle.putString("message", message)
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }
}