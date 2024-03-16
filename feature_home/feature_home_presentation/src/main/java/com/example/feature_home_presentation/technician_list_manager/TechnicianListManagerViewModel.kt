package com.example.feature_home_presentation.technician_list_manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.servicemanager.common_domain.model.Technician
import com.example.servicemanager.common_domain.use_cases.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TechnicianListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
): ViewModel() {

    private val _TechnicianListState = MutableStateFlow<List<Technician>>(emptyList())
    val technicianListState: StateFlow<List<Technician>?> = _TechnicianListState

    var lastDeletedTechnician: Technician? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchTechnicianList()
    }

    fun onEvent(technicianListManagerEvent: TechnicianListManagerEvent) {
        when (technicianListManagerEvent) {
            is TechnicianListManagerEvent.AddTechnician -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createTechnician(technicianListManagerEvent.technician)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.LOADING -> Unit
                        ResourceState.SUCCESS -> {
                            fetchTechnicianList()
                        }
                    }
                    }
                }
            is TechnicianListManagerEvent.DeleteTechnician -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.deleteTechnician(technicianListManagerEvent.technician)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchTechnicianList()
                            _eventFlow.emit(UiEvent.ShowSnackbar("Revert delete"))
                            lastDeletedTechnician = technicianListManagerEvent.technician
                        }
                        ResourceState.LOADING -> Unit
                    }
                    }

                }
            is TechnicianListManagerEvent.ChangeOrder -> Unit
            is TechnicianListManagerEvent.RevertTechnician -> {
                viewModelScope.launch(Dispatchers.IO) {
                val result = appUseCases.createTechnicianWithId(technicianListManagerEvent.technician)
                    when(result.resourceState){
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchTechnicianList()
                            lastDeletedTechnician = null
                        }
                        ResourceState.LOADING -> Unit
                    }
                }
            }
        }
    }

    private fun fetchTechnicianList(){
        var technicianList: List<Technician>? = null
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getTechnicianList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _TechnicianListState.value = list
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

sealed class TechnicianListManagerEvent {
    data class DeleteTechnician(val technician: Technician): TechnicianListManagerEvent()
    data class AddTechnician(val technician: Technician): TechnicianListManagerEvent()
    data class RevertTechnician(val technician: Technician): TechnicianListManagerEvent()
    // TODO Changing order to implement
    data class ChangeOrder(val technicianList: List<Technician>): TechnicianListManagerEvent()
}