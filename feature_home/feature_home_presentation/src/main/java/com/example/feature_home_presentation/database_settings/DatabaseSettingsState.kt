package com.example.feature_home_presentation.database_settings

import com.example.core.util.UiText
import com.example.servicemanager.feature_inspections_domain.model.Inspection

data class DatabaseSettingsState(
    val importedInspectionList: List<Inspection> = emptyList(),
    ) {
}