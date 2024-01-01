package com.example.servicemanager.feature_inspections_presentation.inspection_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Helper.Companion.byteArrayToBitmap
import com.example.core.util.Helper.Companion.bitmapToByteArray
import com.example.core.util.ResourceState
import com.example.core.util.Screen
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections_domain.use_cases.InspectionUseCases
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val inspectionUseCases: InspectionUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var inspectionDetailsIsLoading = true
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
        fetchSignature()
        fetchHospitalList()
        fetchEstStateList()
        fetchInspectionStateList()
        fetchTechnicianList()
    }

    fun onEvent(inspectionDetailsEvent: InspectionDetailsEvent) {
        when (inspectionDetailsEvent) {
            is InspectionDetailsEvent.UpdateInspectionState -> {
                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                    inspection = inspectionDetailsEvent.inspection
                )
            }

            is InspectionDetailsEvent.UpdateSignatureState -> {
                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                    signature = inspectionDetailsEvent.signature
                )
            }

            is InspectionDetailsEvent.SaveInspection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result =
                        inspectionUseCases.saveInspection(inspectionDetailsState.value.inspection)
                    when (result.resourceState) {
                        ResourceState.LOADING -> Unit
                        ResourceState.ERROR -> {
                            _eventFlow.emit(UiEvent.ShowSnackBar(result.data ?: "Uknown error"))
                        }

                        ResourceState.SUCCESS -> {
                            result.data?.let { inspectionId ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    inspection = _inspectionDetailsState.value.inspection.copy(
                                        inspectionId = inspectionId
                                    )
                                )
                            }
                            viewModelScope.launch(Dispatchers.IO) {
                                appUseCases.saveSignature(
                                    inspectionDetailsState.value.inspection.inspectionId,
                                    bitmapToByteArray(inspectionDetailsState.value.signature)
                                )
                            }
                            _eventFlow.emit(UiEvent.NavigateTo(Screen.InspectionListScreen.route))
                        }
                    }
                }
            }
            is InspectionDetailsEvent.UpdateInspection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    inspectionUseCases.updateInspection(inspectionDetailsState.value.inspection)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    appUseCases.updateSignature(inspectionDetailsState.value.inspection.inspectionId, bitmapToByteArray(inspectionDetailsState.value.signature))
                }
            }
            is InspectionDetailsEvent.SetIsInEditMode -> {
                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                    isInEditMode = inspectionDetailsEvent.value
                )
                viewModelScope.launch(Dispatchers.IO) {
                    _eventFlow.emit(UiEvent.SetFieldsIsEditable(inspectionDetailsState.value.isInEditMode))
                }
            }
        }
    }

    private fun fetchInspection() {
        currentInspectionId = savedStateHandle.get<String?>("inspectionId")
        if (currentInspectionId != "0") {
            viewModelScope.launch(Dispatchers.Main) {
                inspectionUseCases
                    .getInspection(inspectionId = currentInspectionId.toString())
                    .collect { result ->
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { inspection ->
                                    _inspectionDetailsState.value =
                                        _inspectionDetailsState.value.copy(
                                            inspection = inspection
                                        )
                                    _eventFlow.emit(UiEvent.UpdateTextFields(inspection))
                                    inspectionDetailsIsLoading = false
                                    setIsLoadingStatus()
                                }
                            }
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> Unit
                        }
                    }
            }
        } else {
            _inspectionDetailsState.value =
                _inspectionDetailsState.value.copy(
                    inspection = Inspection(inspectionId = "0"),
                    isInEditMode = true
                )
            inspectionDetailsIsLoading = false
            setIsLoadingStatus()

        }
    }
    // TODO Bug needs to be fixed - fetches signature even if there no signature
    private fun fetchSignature() {
        if (currentInspectionId != "0") {
            viewModelScope.launch(Dispatchers.Main) {
                appUseCases
                    .getSignature(currentInspectionId.toString())
                    .collect { result ->
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { signature ->
                                    _inspectionDetailsState.value =
                                        _inspectionDetailsState.value.copy(
                                            signature = byteArrayToBitmap(signature)
                                        )
                                }
                            }
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> Unit
                        }
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
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                hospitalList = list,
                            )
                            hospitalListIsLoading = false
                            setIsLoadingStatus()
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
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                inspectionStateList = list,
                            )
                            inspectionStateListIsLoading = false
                            setIsLoadingStatus()
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
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                estStateList = list,
                            )
                            estStateListIsLoading = false
                            setIsLoadingStatus()
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
                            _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                technicianList = list,
                            )
                            technicianListIsLoading = false
                            setIsLoadingStatus()
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
            !inspectionDetailsIsLoading &&
            !hospitalListIsLoading &&
            !estStateListIsLoading &&
            !technicianListIsLoading &&
            !inspectionStateListIsLoading
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
        data class UpdateTextFields(val inspection: Inspection): UiEvent()
        data class NavigateTo(val route: String): UiEvent()
        data class SetFieldsIsEditable(val value: Boolean): UiEvent()
    }
}