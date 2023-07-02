package com.example.servicemanager.feature_inspections.presentation.inspection_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.example.servicemanager.core.util.DefaultTextFieldState
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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


    private var currentInspectionId: Int? = null

    private val _name = mutableStateOf(
        DefaultTextFieldState(
        hint = "Name"
        )
    )
    val name: State<DefaultTextFieldState> = _name

    private val _producer = mutableStateOf(
        DefaultTextFieldState(
            hint = "Producer"
        )
    )
    val producer: State<DefaultTextFieldState> = _producer

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

    var state = mutableStateOf(InspectionDetailsState())

    init {
        fetchHospitalList()
        fetchEstStateList()
        fetchInspectionList()
        fetchInspectionStateList()
        fetchTechnicianList()
    }

    private fun fetchInspectionList(
        query: String = state.value.searchQuery.lowercase(),
        fetchFromApi: Boolean = false
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            inspectionUseCases.getInspectionList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(inspectionList = list)
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
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
                            state.value = state.value.copy(
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
                            state.value = state.value.copy(
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
                            state.value = state.value.copy(
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
                            state.value = state.value.copy(
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
            state.value.inspection.inspectionId.isNotEmpty() &&
            state.value.hospitalList.isNotEmpty() &&
            state.value.estStateList.isNotEmpty() &&
            state.value.technicianList.isNotEmpty() &&
            state.value.inspectionStateList.isNotEmpty()
        ){
            state.value = state.value.copy(
                isLoading = false
            )
        }
        else {
            state.value = state.value.copy(
                isLoading = true
            )
        }
    }

    sealed class UiEvent {
        object SaveInspection: UiEvent()
        data class ShowSnackBar(val messege: String): UiEvent()
    }
}