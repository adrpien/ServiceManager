package com.example.feature_home_presentation.database_settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.feature_home_presentation.home.HomeEvent
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseSettingsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val homeUseCases: HomeUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DatabaseSettingsEvent) {
        when(event) {
            is DatabaseSettingsEvent.AddInspectionList -> {
            }
            is DatabaseSettingsEvent.AddHospital -> {
            }
            is DatabaseSettingsEvent.AddInspectionState -> {
            }
            is DatabaseSettingsEvent.AddRepairState -> {
            }

            is DatabaseSettingsEvent.ManageHospitalList -> {
            }
        }
    }
}
sealed class UiEvent() {
    data class Navigate(val screen: Screen): UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}