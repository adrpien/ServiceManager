package com.example.servicemanager.feature_inspections.presentation.inspection_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.example.servicemanager.core.util.NoteTextFieldState
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.feature_app.domain.use_cases.devices.GetDeviceList
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
import com.example.servicemanager.feature_inspections.presentation.inspection_list.InspectionListState
import com.example.servicemanager.feature_repairs.domain.repository.RepairRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getInspection: GetInspection,
    private val getTechnicianList: GetTechnicianList,
    private val getHospitalList: GetHospitalList,
    private val getEstStateList: GetEstStateList,
    private val getInspectionStateList: GetInspectionStateList,
    private val getDeviceList: GetDeviceList
): ViewModel() {


    private var currentInspectionId: Int? = null

    private val _name = mutableStateOf(
        NoteTextFieldState(
        hint = "Name"
        )
    )
    val name: State<NoteTextFieldState> = _name

    private val _inspectionDetailsState = mutableStateOf(InspectionDetailsState())
    val inspectionDetailsState: State<InspectionDetailsState> = _inspectionDetailsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var state = mutableStateOf(InspectionDetailsState())

    init {
        savedStateHandle.get<String>("noteId")?.let { id ->
            if(id.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch{
                    getInspection(id).also {

                    }
                    noteUseCases.getNote(id)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteDescription.value = noteDescription.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }

    }

    private fun fetchInspectionList(
        query: String = state.value.searchQuery.lowercase(),
        fetchFromApi: Boolean = false
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getInspectionList().collect { result ->
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
            getHospitalList().collect { result ->
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

    private fun fetchDeviceList() {
        viewModelScope.launch(Dispatchers.IO) {
            getDeviceList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(
                                deviceList = list,
                            )
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }    }

    private fun fetchInspectionStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            getInspectionStateList().collect { result ->
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
            getEstStateList().collect { result ->
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
            getTechnicianList().collect { result ->
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
            state.value.deviceList.isNotEmpty() &&
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