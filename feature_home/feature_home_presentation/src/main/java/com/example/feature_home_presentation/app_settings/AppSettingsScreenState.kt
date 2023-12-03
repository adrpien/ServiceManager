package com.example.feature_home_presentation.app_settings

import com.example.core.util.DateFormattingType

data class AppSettingsScreenState(
    val isDarkModeEnabled: Boolean = true,
    val dateFormattingType: DateFormattingType = DateFormattingType.BackSlashStyle(),
    val dateFormattingTypeList: List<DateFormattingType> = listOf()
)
