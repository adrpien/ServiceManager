package com.example.servicemanager.feature_inspections.presentation.inspection_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val inspectionUseCases: InspectionUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var hospitalListIsLoading = true
    private var estStateListIsLoading = true
    private var technicianListIsLoading = true
    private var inspectionStateListIsLoading = true

    private var currentInspectionId: String? = null

    private val _inspectionDetailsState = mutableStateOf(InspectionDetailsState())
    val inspectionDetailsState: State<InspectionDetailsState> = _inspectionDetailsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchInspection()
        fetchHospitalList()
        fetchEstStateList()
        fetchInspectionStateList()
        fetchTechnicianList()
    }

    fun onEvent(inspectionDetailsEvent: InspectionDetailsEvent) {
        when(inspectionDetailsEvent) {
            is InspectionDetailsEvent.SaveInspection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    inspectionUseCases.createInspection(inspectionDetailsEvent.inspection)
                }
            }
            is InspectionDetailsEvent.UpdateInspection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    inspectionUseCases.updateInspection(inspectionDetailsEvent.inspection)
                }
            }
            is InspectionDetailsEvent.UpdateHospital -> {
                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                    inspection = _inspectionDetailsState.value.inspection
                        .copy(hospitalId = inspectionDetailsEvent.hospitalId)
                )
            }

        }
    }

    private fun fetchInspection() {
            currentInspectionId = savedStateHandle.get<String?>("inspectionId")
        viewModelScope.launch(Dispatchers.IO) {
            inspectionUseCases
                .getInspection(inspectionId = currentInspectionId ?: "0")
                .collect { result ->
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { inspection ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    inspection = inspection
                                )
                                _eventFlow.emit(UiEvent.UpdateInspection(inspection))
                            }
                        }
                        ResourceState.LOADING -> Unit
                        ResourceState.ERROR -> Unit
                    }
                }
        }
    }

    private fun fetchHospitalList() {
        viewModelScope.launch(Dispatchers.Main) {
            hospitalListIsLoading = true
            setIsLoadingStatus()
            appUseCases.getHospitalList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            hospitalListIsLoading = false
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
        inspectionStateListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getInspectionStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            inspectionStateListIsLoading = false
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
        estStateListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getEstStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            estStateListIsLoading = false
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
        technicianListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getTechnicianList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            technicianListIsLoading = false
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

        data class UpdateInspection(val text: Inspection): UiEvent()
    }
}