package com.example.shared_preferences_domain

import com.example.core.util.DateFormattingTypes

interface AppPreferences {

    // Date formatting type
    fun getDateFromattingType(): DateFormattingTypes
    fun setDateFormattingType(formattingType: DateFormattingTypes)

    // Dark mode
    fun getIsDarkModeEnabled(): Boolean
    fun setIsDarkModeEnabled(isDarkModeEnabled: Boolean)

    companion object {
        const val KEY_DARK_MODE = "dark_mode"
        const val KEY_DATE_FORMATTING_TYPE = "date_formatting_type"

    }

}