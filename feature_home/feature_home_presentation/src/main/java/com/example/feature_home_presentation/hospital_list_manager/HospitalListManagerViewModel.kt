package com.example.feature_home_presentation.hospital_list_manager

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _hospitalListState = MutableStateFlow<List<Hospital>>(emptyList())
    val hospitalListState: StateFlow<List<Hospital>?> = _hospitalListState

    var lastDeletedHospital: Hospital? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchHospitalList()
    }

    fun onEvent(hospitalListManagerEvent: HospitalListManagerEvent) {
        when (hospitalListManagerEvent) {
            is HospitalListManagerEvent.AddHospital -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createHospital(hospitalListManagerEvent.hospital)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.LOADING -> Unit
                        ResourceState.SUCCESS -> {
                            fetchHospitalList()
                        }
                    }
                    }
                }
            is HospitalListManagerEvent.DeleteHospital -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.deleteHospital(hospitalListManagerEvent.hospital)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchHospitalList()
                            _eventFlow.emit(UiEvent.ShowSnackbar("Revert delete"))
                            lastDeletedHospital = hospitalListManagerEvent.hospital
                        }
                        ResourceState.LOADING -> Unit
                    }
                    }

                }
            is HospitalListManagerEvent.ChangeOrder -> Unit
            is HospitalListManagerEvent.RevertHospital -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createHospitalWithId(hospitalListManagerEvent.hospital)
                    when(result.resourceState){
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchHospitalList()
                            lastDeletedHospital = null
                        }
                        ResourceState.LOADING -> Unit
                    }
                    }
            }
        }
    }

    private fun fetchHospitalList(){
        var hospitalList: List<Hospital>? = null
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getHospitalList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _hospitalListState.value = list
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

sealed class HospitalListManagerEvent {
    data class DeleteHospital(val hospital: Hospital): HospitalListManagerEvent()
    data class AddHospital(val hospital: Hospital): HospitalListManagerEvent()
    data class RevertHospital(val hospital: Hospital): HospitalListManagerEvent()
    data class ChangeOrder(val hospitalList: List<Hospital>): HospitalListManagerEvent()
}