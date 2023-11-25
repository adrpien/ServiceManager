package com.example.core.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.core.util.DateFormattingTypes
import com.example.core.util.DateFormattingTypes.Companion.getDateFormattingTypeWithString

class AppPreferencesImplementation(
    context: Context
): AppPreferences {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    override fun getDateFormattingType(): DateFormattingTypes {
        return getDateFormattingTypeWithString(sharedPreferences.getString(AppPreferences.DATE_FORMATTING_TYPE, DateFormattingTypes.DashStyle().value) ?: DateFormattingTypes.DashStyle().value)
    }

    override fun setDateFormattingType(formattingType: DateFormattingTypes) {
        sharedPreferences.edit()
            .putString(AppPreferences.DATE_FORMATTING_TYPE, formattingType.formatting)
            .apply()
    }

    override fun getIsDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(AppPreferences.DARK_MODE, true)
    }

    override fun setIsDarkModeEnabled(isDarkModeEnabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(AppPreferences.DARK_MODE, isDarkModeEnabled)
            .apply()
    }


}