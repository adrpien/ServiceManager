package com.example.feature_home_presentation.repair_state_list_manager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepairStateListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _repairStateListState = mutableStateOf<List<RepairState>>(emptyList())
    val repairStateListState: State<List<RepairState>?> = _repairStateListState

    var lastDeletedRepairState: RepairState? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchRepairStateList()
    }

    fun onEvent(repairStateListManagerEvent: RepairStateListManagerEvent) {
        when (repairStateListManagerEvent) {
            is RepairStateListManagerEvent.AddRepairState -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createRepairState(repairStateListManagerEvent.repairState)
                        when(result.resourceState) {
                            ResourceState.ERROR -> Unit
                            ResourceState.LOADING -> Unit
                            ResourceState.SUCCESS -> {
                                fetchRepairStateList()
                            }
                        }
                    }
                }
            is RepairStateListManagerEvent.DeleteRepairState -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.deleteRepairState(repairStateListManagerEvent.repairState)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchRepairStateList()
                            _eventFlow.emit(UiEvent.ShowSnackbar("Revert delete"))
                            lastDeletedRepairState = repairStateListManagerEvent.repairState
                        }
                        ResourceState.LOADING -> Unit
                    }
                    }

                }
            is RepairStateListManagerEvent.ChangeOrder -> Unit
            is RepairStateListManagerEvent.RevertRepairState -> {
                viewModelScope.launch(Dispatchers.IO) {
                val result = appUseCases.createRepairStateWithId(repairStateListManagerEvent.repairState)
                when(result.resourceState){
                    ResourceState.ERROR -> Unit
                    ResourceState.SUCCESS -> {
                        fetchRepairStateList()
                        lastDeletedRepairState = null
                    }
                    ResourceState.LOADING -> Unit
                }
                }
            }
        }
    }

    private fun fetchRepairStateList(){
        var repairStateList: List<RepairState>? = null
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getRepairStateList().collect() { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _repairStateListState.value = list
                        }
                    }
                    ResourceState.ERROR -> Unit
                    ResourceState.LOADING -> Unit
                }
            }
        }
    }


}

sealed class UiEvent() {
    data class ShowSnackbar(val message: String): UiEvent()
}

sealed class RepairStateListManagerEvent() {
    data class DeleteRepairState(val repairState: RepairState): RepairStateListManagerEvent()
    data class AddRepairState(val repairState: RepairState): RepairStateListManagerEvent()
    data class RevertRepairState(val repairState: RepairState): RepairStateListManagerEvent()
    data class ChangeOrder(val repairStateList: List<RepairState>): RepairStateListManagerEvent()
}