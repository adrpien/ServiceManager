package com.example.feature_home_presentation.app_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.preferences.AppPreferences
import com.example.core.util.DateFormattingType
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsScreenViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val appUseCases: AppUseCases
): ViewModel() {

    private val _appSettingsScreenState = mutableStateOf(AppSettingsScreenState())
    val appSettingsScreenState: State<AppSettingsScreenState> = _appSettingsScreenState


    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                viewModelScope.launch(Dispatchers.IO) {
                    _appSettingsScreenState.value =
                        _appSettingsScreenState.value.copy(isDarkModeEnabled = event.value)
                    appPreferences.setIsDarkModeEnabled(event.value)
                    _eventFlow.emit(UiEvent.ShowSnackbar("Changes will be visible next time you launch the app"))
                }
            }
                is AppSettingsScreenEvent.SetDateFormattingType -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        _appSettingsScreenState.value =
                            _appSettingsScreenState.value.copy(dateFormattingType = event.value)
                        appPreferences.setDateFormattingType(event.value)
                        _eventFlow.emit(UiEvent.ShowSnackbar("Changes will be visible next time you launch the app"))
                    }
                }
        }
    }
}

sealed class UiEvent() {
    data class ShowSnackbar(val message: String): UiEvent()
}