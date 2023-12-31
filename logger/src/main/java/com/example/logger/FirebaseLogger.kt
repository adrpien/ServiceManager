package com.example.logger

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.core.util.MapperExtensionFunction.toMap
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseLogger<T>(
    val firebaseAnalytics: FirebaseAnalytics
): AppLogger<T> {

    override suspend fun logEventWithBundle(eventLogType: EventLogType, message: String, params: Bundle) {
        params.putString("message", message)
        firebaseAnalytics.logEvent(eventLogType.logId, params)
    }

    // TODO logEvent in Logger should be suspend function
    override suspend fun logEvent(
        eventLogType: EventLogType,
        message: String,
        dataClassObject: T
    ) {
        val bundle = bundleOf()
        bundle.putString("type", eventLogType.logId)
        bundle.putString("message", message)
        val map = dataClassObject?.toMap()
        map?.forEach { key, value ->
            bundle.putString(key, value.toString())
        }
        firebaseAnalytics.logEvent(eventLogType.logId, bundle)
    }

}