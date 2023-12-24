package com.example.logger

import android.os.Bundle


interface AppLogger<T> {
    fun logEventWithBundle(eventLogType: EventLogType, message: String = "", params: Bundle)
    fun logEvent(eventLogType: EventLogType, message: String = "", dataClassObject: T)

}

