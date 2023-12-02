package com.example.feature_home_presentation.database_settings

import com.example.core.util.DateFormattingType

sealed class AppSettingsScreenEvent(){
    data class SetDarkMode(val value: Boolean): AppSettingsScreenEvent()
    data class SetDateFormattingType(val value: DateFormattingType): AppSettingsScreenEvent()
}
