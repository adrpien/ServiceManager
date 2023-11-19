package com.example.logger_domain.logger

import android.os.Bundle
import com.example.logger_domain.util.EventLogType
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_repairs_domain.model.Repair

interface AppLogger {
    fun logEvent(eventLogType: EventLogType, message: String, params: Bundle)
    fun logInspection(eventLogType: EventLogType, message: String, inspection: Inspection, userId: String)
    fun logRepair(eventLogType: EventLogType, message: String, repair: Repair, userId: String)

}

