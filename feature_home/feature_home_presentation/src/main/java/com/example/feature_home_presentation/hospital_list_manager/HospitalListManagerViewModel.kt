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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    private val homeUseCases: HomeUseCases
): ViewModel() {

    private val _hospitalListState = mutableStateOf<List<Hospital>>(emptyList())
    val hospitalListState: State<List<Hospital>?> = _hospitalListState

    private val _deletedHospitalListState = mutableStateOf<List<Hospital>>(emptyList())
    val deletedHospitalListState: State<List<Hospital>?> = _deletedHospitalListState

    private val _lastDeletedHospitalState = mutableStateOf<Hospital>(Hospital())
    val lastDeletedHospitalState: State<Hospital> = _lastDeletedHospitalState



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchHospitalList()
    }

    fun onEvent(hospitalListManagerEvent: HospitalListManagerEvent) {
        when (hospitalListManagerEvent) {
            is HospitalListManagerEvent.AddHospital -> {
                val hospitalToAdd = hospitalListManagerEvent.hospital
                viewModelScope.launch(Dispatchers.IO) {
                    _hospitalListState.value =
                        _hospitalListState.value.plus(hospitalToAdd)
                    _deletedHospitalListState.value = _deletedHospitalListState.value.minus(hospitalListManagerEvent.hospital)
                }
            }

            is HospitalListManagerEvent.DeleteHospital -> {
                val hospitalToDelete =
                    _hospitalListState.value?.first { it.hospitalId == hospitalListManagerEvent.hospitalId }
                hospitalToDelete?.let {
                    _lastDeletedHospitalState.value = hospitalToDelete
                    viewModelScope.launch(Dispatchers.IO) {
                        _hospitalListState.value = _hospitalListState.value.minus(hospitalToDelete)
                        _deletedHospitalListState.value =
                            _deletedHospitalListState.value.plus(hospitalToDelete)
                        _eventFlow.emit(UiEvent.ShowSnackbar(""))
                    }
                }
            }

            is HospitalListManagerEvent.ChangeOrder -> Unit
            HospitalListManagerEvent.SaveChanges -> Unit
            HospitalListManagerEvent.UndoChanges -> {
                lastDeletedHospitalState.value?. let {
                    _hospitalListState.value = _hospitalListState.value.plus(lastDeletedHospitalState.value)
                    _deletedHospitalListState.value = _deletedHospitalListState.value.minus(lastDeletedHospitalState.value)
                }
            }
        }
    }

    private fun fetchHospitalList(){
        var hospitalList: List<Hospital>? = null
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getHospitalList().collect() { result ->
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

sealed class UiEvent() {
    data class ShowSnackbar(val message: String): UiEvent()
}

sealed class HospitalListManagerEvent() {
    data class DeleteHospital(val hospitalId: String): HospitalListManagerEvent()
    data class AddHospital(val hospital: Hospital): HospitalListManagerEvent()
    object SaveChanges: HospitalListManagerEvent()
    object UndoChanges: HospitalListManagerEvent()
    data class ChangeOrder(val hospitalList: List<Hospital>): HospitalListManagerEvent()
}