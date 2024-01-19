package com.example.feature_home_presentation.inspection_state_list_manager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionStateListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _inspectionStateListState = MutableStateFlow<List<InspectionState>>(emptyList())
    val inspectionStateListState: StateFlow<List<InspectionState>?> = _inspectionStateListState

    var lastDeletedInspectionState: InspectionState? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchInspectionStateList()
    }

    fun onEvent(inspectionStateListManagerEvent: InspectionStateListManagerEvent) {
        when (inspectionStateListManagerEvent) {
            is InspectionStateListManagerEvent.AddInspectionState -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createInspectionState(inspectionStateListManagerEvent.inspectionState)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.LOADING -> Unit
                        ResourceState.SUCCESS -> {
                            fetchInspectionStateList()
                        }
                    }
                    }
                }
            is InspectionStateListManagerEvent.DeleteInspectionState -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.deleteInspectionState(inspectionStateListManagerEvent.inspectionState)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchInspectionStateList()
                            _eventFlow.emit(UiEvent.ShowSnackbar("Revert delete"))
                            lastDeletedInspectionState = inspectionStateListManagerEvent.inspectionState
                        }
                        ResourceState.LOADING -> Unit
                    }
                    }

                }
            is InspectionStateListManagerEvent.ChangeOrder -> Unit
            is InspectionStateListManagerEvent.RevertInspectionState -> {
                val result = viewModelScope.launch(Dispatchers.IO) {
                val result = appUseCases.createInspectionStateWithId(inspectionStateListManagerEvent.inspectionState)
                    when(result.resourceState){
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchInspectionStateList()
                            lastDeletedInspectionState = null
                        }
                        ResourceState.LOADING -> Unit
                    }
                }
            }
        }
    }

    private fun fetchInspectionStateList(){
        var inspectionStateList: List<InspectionState>? = null
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getInspectionStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _inspectionStateListState.value = list
                        }
                    }
                    ResourceState.ERROR -> Unit
                    ResourceState.LOADING -> Unit
                }
            }
        }
    }


}

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}

sealed class InspectionStateListManagerEvent {
    data class DeleteInspectionState(val inspectionState: InspectionState): InspectionStateListManagerEvent()
    data class AddInspectionState(val inspectionState: InspectionState): InspectionStateListManagerEvent()
    data class RevertInspectionState(val inspectionState: InspectionState): InspectionStateListManagerEvent()
    data class ChangeOrder(val inspectionStateList: List<InspectionState>): InspectionStateListManagerEvent()
}