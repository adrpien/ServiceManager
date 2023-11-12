package com.example.logger_data.logger

import com.example.logger_domain.AppLogger
import com.example.logger_domain.EventLogType

class FirebaseLogger(
): AppLogger
{
    override fun logEvent(eventLogType: EventLogType, message: String) {

    }
}