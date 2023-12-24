package com.example.logger

sealed class EventLogType(val logId: String) {
    data class ExceptionLog(val id: String = "exception_log_event"): EventLogType(id)
    data class RecordUpdateLog(val id: String = "record_update_log_event"): EventLogType(id)
    data class NewRecordLog(val id: String = "new_record_log_event"): EventLogType(id)
    data class DeleteRecordLog(val id: String = "delete_record_log_event"): EventLogType(id)
}