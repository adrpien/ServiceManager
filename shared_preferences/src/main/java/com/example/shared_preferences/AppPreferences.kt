package com.example.shared_preferences

import com.example.core.util.DateFormattingType

interface AppPreferences {

    // Date formatting type
    fun getDateFormattingType(): DateFormattingType
    fun setDateFormattingType(formattingType: DateFormattingType)

    // Dark mode
    fun getIsDarkModeEnabled(): Boolean
    fun setIsDarkModeEnabled(isDarkModeEnabled: Boolean)

    companion object {
        const val DARK_MODE = "dark_mode"
        const val DATE_FORMATTING_TYPE = "date_formatting_type"
    }

}