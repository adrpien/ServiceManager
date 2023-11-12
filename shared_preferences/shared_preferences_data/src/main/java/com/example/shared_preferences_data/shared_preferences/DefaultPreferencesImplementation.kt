package com.example.shared_preferences_data.shared_preferences

import android.content.SharedPreferences
import com.example.core.util.DateFormattingTypes
import com.example.core.util.DateFormattingTypes.Companion.getDateFormattingTypeWithString
import com.example.shared_preferences_domain.AppPreferences

class DefaultPreferencesImplementation(
    private val sharedPreferences: SharedPreferences
): AppPreferences {
    override fun getDateFromattingType(): DateFormattingTypes {
        return getDateFormattingTypeWithString(sharedPreferences.getString(AppPreferences.KEY_DATE_FORMATTING_TYPE, DateFormattingTypes.DashStyle().value) ?: DateFormattingTypes.DashStyle().value)
    }

    override fun setDateFormattingType(formattingType: DateFormattingTypes) {
        sharedPreferences.edit()
            .putString(AppPreferences.KEY_DATE_FORMATTING_TYPE, formattingType.formatting)
            .apply()
    }

    override fun getIsDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(AppPreferences.KEY_DARK_MODE, true)
    }

    override fun setIsDarkModeEnabled(isDarkModeEnabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(AppPreferences.KEY_DARK_MODE, isDarkModeEnabled)
            .apply()
    }


}