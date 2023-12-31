package com.example.logger

import android.os.Bundle


interface AppLogger<T> {
    suspend fun logEventWithBundle(eventLogType: EventLogType, message: String = "", params: Bundle)
    suspend fun logEvent(eventLogType: EventLogType, message: String = "", dataClassObject: T)

}

