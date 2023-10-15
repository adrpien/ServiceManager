package com.example.core.data

import android.content.SharedPreferences
import com.example.core.domain.Preferences
import com.example.core.util.DateFormattingTypes
import com.example.core.util.DateFormattingTypes.Companion.getDateFormattingTypeWithString


class DefaultPreferencesImplementation(
    private val sharedPreferences: SharedPreferences
): Preferences {
    override fun getDateFromattingType(): DateFormattingTypes {
        return getDateFormattingTypeWithString(sharedPreferences.getString(Preferences.KEY_DATE_FORMATTING_TYPE, DateFormattingTypes.DashStyle().value) ?: DateFormattingTypes.DashStyle().value)
    }

    override fun setDateFormattingType(formattingType: DateFormattingTypes) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_DATE_FORMATTING_TYPE, formattingType.formatting)
            .apply()
    }

    override fun getIsDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(Preferences.KEY_DARK_MODE, true)
    }

    override fun setIsDarkModeEnabled(isDarkModeEnabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(Preferences.KEY_DARK_MODE, isDarkModeEnabled)
            .apply()
    }


}