package com.example.feature_home_presentation.database_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.core.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppSettingsScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences
): ViewModel() {

    private val _appSettingsScreenState = mutableStateOf(AppSettingsScreenState())
    val appSettingsScreenState: State<AppSettingsScreenState> = _appSettingsScreenState

    init {
        fetchPreferences()
    }

    private fun fetchPreferences(): AppSettingsScreenState {
            val isDarkModeEnabled = appPreferences.getIsDarkModeEnabled()
            val dateFormattingType = appPreferences.getDateFormattingType()
        return AppSettingsScreenState(
            isDarkModeEnabled = isDarkModeEnabled,
            dateFormattingType = dateFormattingType
        )

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
