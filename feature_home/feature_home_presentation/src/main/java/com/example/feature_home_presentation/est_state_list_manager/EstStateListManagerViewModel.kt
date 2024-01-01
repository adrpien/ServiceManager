package com.example.feature_home_presentation.estState_list_manager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstStateListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _estStateListState = mutableStateOf<List<EstState>>(emptyList())
    val estStateListState: State<List<EstState>?> = _estStateListState

    var lastDeletedEstState: EstState? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchEstStateList()
    }

    fun onEvent(estStateListManagerEvent: EstStateListManagerEvent) {
        when (estStateListManagerEvent) {
            is EstStateListManagerEvent.AddEstState -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createEstState(estStateListManagerEvent.estState)
                        when(result.resourceState) {
                            ResourceState.ERROR -> Unit
                            ResourceState.LOADING -> Unit
                            ResourceState.SUCCESS -> {
                                fetchEstStateList()
                            }
                        }
                    }
                }
            is EstStateListManagerEvent.DeleteEstState -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.deleteEstState(estStateListManagerEvent.estState)
                        when(result.resourceState) {
                            ResourceState.ERROR -> Unit
                            ResourceState.SUCCESS -> {
                                fetchEstStateList()
                                _eventFlow.emit(UiEvent.ShowSnackbar("Revert delete"))
                                lastDeletedEstState = estStateListManagerEvent.estState
                            }
                            ResourceState.LOADING -> Unit
                        }
                    }

                }
            is EstStateListManagerEvent.ChangeOrder -> Unit
            is EstStateListManagerEvent.RevertEstState -> {
                val result = viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createEstStateWithId(estStateListManagerEvent.estState)
                        when(result.resourceState){
                            ResourceState.ERROR -> Unit
                            ResourceState.SUCCESS -> {
                                fetchEstStateList()
                                lastDeletedEstState = null
                            }
                            ResourceState.LOADING -> Unit
                        }
                    }
            }
        }
    }

    private fun fetchEstStateList(){
        var estStateList: List<EstState>? = null
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getEstStateList().collect() { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _estStateListState.value = list
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

sealed class EstStateListManagerEvent() {
    data class DeleteEstState(val estState: EstState): EstStateListManagerEvent()
    data class AddEstState(val estState: EstState): EstStateListManagerEvent()
    data class RevertEstState(val estState: EstState): EstStateListManagerEvent()
    data class ChangeOrder(val estStateList: List<EstState>): EstStateListManagerEvent()
}