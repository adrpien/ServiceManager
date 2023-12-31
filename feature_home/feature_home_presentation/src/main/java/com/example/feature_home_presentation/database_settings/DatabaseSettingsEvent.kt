package com.example.feature_home_presentation.database_settings

import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import java.io.File
import java.io.InputStream

sealed class DatabaseSettingsEvent {
    data class ImportInspections(val inputStream: InputStream): DatabaseSettingsEvent()
    object SaveInspections: DatabaseSettingsEvent()

}