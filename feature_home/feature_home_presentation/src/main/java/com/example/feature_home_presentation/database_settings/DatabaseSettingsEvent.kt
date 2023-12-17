package com.example.feature_home_presentation.database_settings

import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_inspections_domain.model.Inspection

sealed class DatabaseSettingsEvent {
    data class AddInspectionList(val inspectionList: List<Inspection>): DatabaseSettingsEvent()
    data class AddHospital(val hospital: Hospital): DatabaseSettingsEvent()
    data class AddInspectionState(val inspectionState: InspectionState): DatabaseSettingsEvent()
    data class AddRepairState(val repairState: RepairState): DatabaseSettingsEvent()
    data class ManageHospitalList(val repairState: RepairState): DatabaseSettingsEvent()


}