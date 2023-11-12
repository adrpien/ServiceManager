package com.example.logger_domain

interface AppLogger {

    fun logEvent(eventLogType: EventLogType, message: String)

}

sealed class EventLogType(val logId: String) {
    data class ExceptionLog(val id: String = "EXCEPTION_LOG_EVENT"): EventLogType(id)
    data class SuccessLog(val id: String = "SUCCES_LOG_EVENT"): EventLogType(id)
}