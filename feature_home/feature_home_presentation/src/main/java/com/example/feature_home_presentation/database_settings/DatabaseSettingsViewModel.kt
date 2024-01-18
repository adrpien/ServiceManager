package com.example.feature_home_presentation.database_settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.feature_home_presentation.R
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import com.example.servicemanager.feature_inspections_domain.use_cases.InspectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseSettingsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val homeUseCases: HomeUseCases,
    private val appUseCases: AppUseCases,
    private val inspectionUseCases: InspectionUseCases
): ViewModel() {

    private var _databaseSettingsState = MutableStateFlow<DatabaseSettingsState>(DatabaseSettingsState())
    val databaseSettingsState: StateFlow<DatabaseSettingsState> = _databaseSettingsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DatabaseSettingsEvent) {
        when(event) {
            is DatabaseSettingsEvent.ImportInspections -> {
                viewModelScope.launch(Dispatchers.IO) {
                    homeUseCases.importInspectionsFromFile(event.inputStream).collect() { result ->
                        when(result.resourceState) {
                            ResourceState.ERROR -> {
                                _eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: UiText.StringResource(
                                    R.string.unknown_error)))
                                _eventFlow.emit(UiEvent.HideImportInspectionsDialogs)
                            }

                            ResourceState.SUCCESS -> {
                                result.message?.let {text ->
                                    _eventFlow.emit(UiEvent.UpdateImportInspectionsLoadingDialogState(
                                        text = text,
                                        counter = result.data?.size
                                    ))
                                    _eventFlow.emit(UiEvent.UpdateImportInspectionsDialogState(
                                        text = text,
                                        counter = result.data?.size
                                    ))
                                }
                                result.data?.let { list ->
                                    _databaseSettingsState.value = _databaseSettingsState.value.copy(
                                        importedInspectionList = list
                                    )
                                    _eventFlow.emit(UiEvent.ShowImportInspectionsDialog)
                                }
                            }
                            ResourceState.LOADING -> {
                                result.data?.let { list ->
                                    _databaseSettingsState.value = _databaseSettingsState.value.copy(
                                        importedInspectionList = list,
                                    )
                                }
                                result.message?.let {text ->
                                    _eventFlow.emit(UiEvent.UpdateImportInspectionsLoadingDialogState(
                                        text = text,
                                        counter = result.data?.size
                                    ))
                                    _eventFlow.emit(UiEvent.UpdateImportInspectionsDialogState(
                                        text = text,
                                        counter = result.data?.size
                                    ))
                                }

                            }
                        }
                    }
                }
            }

            DatabaseSettingsEvent.SaveInspections -> {
                viewModelScope.launch(Dispatchers.IO) {
                    homeUseCases.saveInspections(_databaseSettingsState.value.importedInspectionList).collect() { result ->
                        when(result.resourceState) {
                            ResourceState.SUCCESS -> {
                                _eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: UiText.StringResource(R.string.unknown_error)))
                            }
                            ResourceState.ERROR -> Unit
                            ResourceState.LOADING -> {
                             }
                        }
                    }
                }
            }
        }
    }
}

sealed class UiEvent() {
    data class Navigate(val screen: Screen): UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
    object ShowImportInspectionsDialog: UiEvent()
    object HideImportInspectionsDialogs: UiEvent()
    data class UpdateImportInspectionsLoadingDialogState(val text: UiText, val counter: Int?): UiEvent()
    data class UpdateImportInspectionsDialogState(val text: UiText, val counter: Int?): UiEvent()

}