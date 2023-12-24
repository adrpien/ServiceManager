package com.example.logger

import android.os.Bundle
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_repairs_domain.model.Repair

interface AppLogger {
    fun logEvent(eventLogType: EventLogType, message: String = "", params: Bundle)
    fun logInspection(eventLogType: EventLogType, message: String = "", inspection: Inspection)
    fun logRepair(eventLogType: EventLogType, message: String = "", repair: Repair)
    fun logHospital(eventLogType: EventLogType, message: String = "", hospital: Hospital)
    fun logTechnician(eventLogType: EventLogType, message: String = "", technician: Technician)
    fun logEstState(eventLogType: EventLogType, message: String = "", estState: EstState)
    fun logRepairState(eventLogType: EventLogType, message: String = "", repairState: RepairState)
    fun logInspectionState(eventLogType: EventLogType, message: String = "", inspectionState: InspectionState)
    fun logUserType(eventLogType: EventLogType, message: String = "", userType: UserType)


}

