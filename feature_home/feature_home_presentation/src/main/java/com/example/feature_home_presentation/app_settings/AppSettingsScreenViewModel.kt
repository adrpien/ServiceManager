package com.example.feature_home_presentation.app_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.core.preferences.AppPreferences
import com.example.core.util.DateFormattingType
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSettingsScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val appUseCases: AppUseCases
): ViewModel() {

    private val _appSettingsScreenState = mutableStateOf(AppSettingsScreenState())
    val appSettingsScreenState: State<AppSettingsScreenState> = _appSettingsScreenState



    init {
        val preferences = fetchPreferences()
        val dateTypes = fetchDateFormattingTypes()


        _appSettingsScreenState.value = _appSettingsScreenState.value.copy(
            isDarkModeEnabled = preferences.isDarkModeEnabled,
            dateFormattingType = preferences.dateFormattingType,
            dateFormattingTypeList = dateTypes
        )


    }

    private fun fetchPreferences(): AppSettingsScreenState {
            val isDarkModeEnabled = appPreferences.getIsDarkModeEnabled()
            val dateFormattingType = appPreferences.getDateFormattingType()
        return AppSettingsScreenState(
            isDarkModeEnabled = isDarkModeEnabled,
            dateFormattingType = dateFormattingType
        )

    }

    private fun fetchDateFormattingTypes(): List<DateFormattingType> {
        return appUseCases.getDateFormattingTypes()
    }

    fun onEvent(event: AppSettingsScreenEvent) {
        when(event) {
            is AppSettingsScreenEvent.SetDarkMode -> {
                _appSettingsScreenState.value = _appSettingsScreenState.value.copy(isDarkModeEnabled = event.value)
                appPreferences.setIsDarkModeEnabled(event.value)
            }
            is AppSettingsScreenEvent.SetDateFormattingType -> {
                _appSettingsScreenState.value = _appSettingsScreenState.value.copy(dateFormattingType = event.value)
                appPreferences.setDateFormattingType(event.value)
            }
        }
    }

    sealed class UIEvent() {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}
