package com.example.feature_home_presentation.database_settings

import com.example.core.util.DateFormattingType

data class AppSettingsScreenState(
    val isDarkModeEnabled: Boolean = true,
    val dateFormattingType: DateFormattingType = DateFormattingType.BackSlashStyle()
)
