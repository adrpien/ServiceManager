package com.example.servicemanager.feature_inspections.presentation.inspection_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.core.util.DefaultTextFieldState
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionDetailsViewModel @Inject constructor(
    private val inspectionUseCases: InspectionUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var currentInspectionId: Int? = null

    private val _deviceName = mutableStateOf(
        DefaultTextFieldState(
        hint = "Name"
        )
    )
    val deviceName: State<DefaultTextFieldState> = _deviceName

    private val _deviceProducer = mutableStateOf(
        DefaultTextFieldState(
            hint = "Producer"
        )
    )
    val deviceProducer: State<DefaultTextFieldState> = _deviceProducer

    private val _hospital = mutableStateOf(DefaultTextFieldState(
        hint = "Hospital"
    ))
    val hospital: State<DefaultTextFieldState> = _hospital

    private val _localization = mutableStateOf(DefaultTextFieldState(
        hint = "Localization"
    ))
    val localization: State<DefaultTextFieldState> = _localization

    private val _inspectionDetailsState = mutableStateOf(InspectionDetailsState())
    val inspectionDetailsState: State<InspectionDetailsState> = _inspectionDetailsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchHospitalList()
        fetchEstStateList()
        fetchInspectionStateList()
        fetchTechnicianList()
    }

    fun onEvent(inspectionDetailsEvent: InspectionDetailsEvent) {
        when(inspectionDetailsEvent) {
            is InspectionDetailsEvent.saveInspection -> {

            }
            is InspectionDetailsEvent.updateInspection -> {

            }
        }
    }

    private fun fetchHospitalList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getHospitalList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                hospitalList = list,
                            )
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }
    }

    private fun fetchInspectionStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getInspectionStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                inspectionStateList = list,
                            )
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }
    }

    private fun fetchEstStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getEstStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                estStateList = list,
                            )
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }
    }

    private fun fetchTechnicianList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getTechnicianList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                technicianList = list,
                            )
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }
    }

    private fun setIsLoadingStatus() {
        if(
            _inspectionDetailsState.value.inspection.inspectionId.isNotEmpty() &&
            _inspectionDetailsState.value.hospitalList.isNotEmpty() &&
            _inspectionDetailsState.value.estStateList.isNotEmpty() &&
            _inspectionDetailsState.value.technicianList.isNotEmpty() &&
            _inspectionDetailsState.value.inspectionStateList.isNotEmpty()
        ){
            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                isLoading = false
            )
        }
        else {
            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                isLoading = true
            )
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val messege: String): UiEvent()
    }
}