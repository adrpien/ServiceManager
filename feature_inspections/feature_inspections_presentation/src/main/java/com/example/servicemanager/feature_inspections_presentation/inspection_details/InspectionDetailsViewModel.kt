package com.example.servicemanager.feature_inspections_presentation.inspection_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Helper.Companion.byteArrayToBitmap
import com.example.core.util.Helper.Companion.bitmapToByteArray
import com.example.core.util.ResourceState
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.servicemanager.common_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections_domain.use_cases.InspectionUseCases
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _inspectionDetailsState = MutableStateFlow(InspectionDetailsState())
    val inspectionDetailsState: StateFlow<InspectionDetailsState> = _inspectionDetailsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchHospitalList()
        fetchEstStateList()
        fetchInspectionStateList()
        fetchTechnicianList()
        fetchInspection()
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
                    withContext(Dispatchers.Main) {
                        when (result.resourceState) {
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> {
                                if (result.data == "CONNECTION_ERROR") {
                                    appUseCases.saveSignature(
                                        inspectionDetailsState.value.inspection.inspectionId,
                                        bitmapToByteArray(inspectionDetailsState.value.signature)
                                    )
                                }
                                _eventFlow.emit(
                                    UiEvent.ShowSnackBar(
                                        result.message ?: UiText.DynamicString("Uknown error")
                                    )
                                )
                            }

                            ResourceState.SUCCESS -> {
                                val userId = savedStateHandle.get<String?>("userId") ?: "0"
                                _eventFlow.emit(UiEvent.NavigateTo(Screen.InspectionListScreen.withArgs(userId)))
                                result.data?.let { inspectionId ->
                                    _inspectionDetailsState.value =
                                        _inspectionDetailsState.value.copy(
                                            inspection = _inspectionDetailsState.value.inspection.copy(
                                                inspectionId = inspectionId
                                            )
                                        )
                                }
                                appUseCases.saveSignature(
                                    inspectionDetailsState.value.inspection.inspectionId,
                                    bitmapToByteArray(inspectionDetailsState.value.signature)
                                )
                            }
                        }
                    }
                }

            }
            is InspectionDetailsEvent.UpdateInspection -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result =
                        inspectionUseCases.updateInspection(inspectionDetailsState.value.inspection)
                    withContext(Dispatchers.Main) {
                        when (result.resourceState) {
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> {
                                if (result.data == "CONNECTION_ERROR") {
                                    appUseCases.updateSignature(
                                        inspectionDetailsState.value.inspection.inspectionId,
                                        bitmapToByteArray(inspectionDetailsState.value.signature)
                                    )
                                }
                                _eventFlow.emit(
                                    UiEvent.ShowSnackBar(
                                        result.message ?: UiText.DynamicString("Uknown error")
                                    )
                                )
                            }

                            ResourceState.SUCCESS -> {
                                val userId = savedStateHandle.get<String?>("userId") ?: "0"
                                _eventFlow.emit(UiEvent.NavigateTo(Screen.InspectionListScreen.withArgs(userId)))
                                result.data?.let { inspectionId ->
                                    _inspectionDetailsState.value =
                                        _inspectionDetailsState.value.copy(
                                            inspection = _inspectionDetailsState.value.inspection.copy(
                                                inspectionId = inspectionId
                                            )
                                        )
                                }
                                appUseCases.updateSignature(
                                    inspectionDetailsState.value.inspection.inspectionId,
                                    bitmapToByteArray(inspectionDetailsState.value.signature)
                                )
                            }
                        }
                    }
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

            else -> Unit
        }
    }

    private fun fetchInspection() {
        currentInspectionId = savedStateHandle.get<String?>("inspectionId")
        if (currentInspectionId != "0") {
            viewModelScope.launch(Dispatchers.IO) {
                inspectionUseCases
                    .getInspection(inspectionId = currentInspectionId.toString())
                    .collect { result ->
                        withContext(Dispatchers.Main) {
                            when (result.resourceState) {
                                ResourceState.SUCCESS -> {
                                    result.data?.let { inspection ->
                                        _inspectionDetailsState.value =
                                            _inspectionDetailsState.value.copy(
                                                inspection = inspection
                                            )
                                        _eventFlow.emit(UiEvent.UpdateTextFields(inspection))
                                        inspectionDetailsIsLoading = false
                                        result.data?.let {
                                            fetchSignature(it)
                                        }
                                        setIsLoadingStatus()
                                    }
                                }

                                ResourceState.LOADING -> {
                                    result.data?.let { inspection ->
                                        _inspectionDetailsState.value =
                                            _inspectionDetailsState.value.copy(
                                                inspection = inspection
                                            )
                                        _eventFlow.emit(UiEvent.UpdateTextFields(inspection))
                                        inspectionDetailsIsLoading = true
                                        result.data?.let {
                                            fetchSignature(it)
                                        }
                                        setIsLoadingStatus()
                                    }
                                }

                                ResourceState.ERROR -> {
                                    inspectionDetailsIsLoading = false
                                    setIsLoadingStatus()
                                }
                            }
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
    private fun fetchSignature(inspection: Inspection) {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases
                .getSignature(inspection.signatureId)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { signature ->
                                    _inspectionDetailsState.value =
                                        _inspectionDetailsState.value.copy(
                                            signature = byteArrayToBitmap(signature)
                                        )
                                }
                            }

                            ResourceState.LOADING -> {
                                result.data?.let { signature ->
                                    _inspectionDetailsState.value =
                                        _inspectionDetailsState.value.copy(
                                            signature = byteArrayToBitmap(signature)
                                        )
                                }
                            }

                            ResourceState.ERROR -> Unit
                        }
                    }
                }
        }
    }

    private fun fetchHospitalList() {
        viewModelScope.launch(Dispatchers.IO) {
            hospitalListIsLoading = true
            setIsLoadingStatus()
            appUseCases.getHospitalList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    hospitalList = list,
                                )
                                hospitalListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    hospitalList = list,
                                )
                                hospitalListIsLoading = true
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.ERROR -> {
                            hospitalListIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
                }
            }
        }
    }

    private fun fetchInspectionStateList() {
        inspectionStateListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getInspectionStateList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    inspectionStateList = list,
                                )
                                inspectionStateListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    inspectionStateList = list,
                                )
                                inspectionStateListIsLoading = true
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.ERROR -> {
                            inspectionStateListIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
                }
            }
        }
    }

    private fun fetchEstStateList() {
        estStateListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getEstStateList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    estStateList = list,
                                )
                                estStateListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    estStateList = list,
                                )
                                estStateListIsLoading = true
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.ERROR -> {
                            estStateListIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
                }
            }
        }
    }

    private fun fetchTechnicianList() {
        technicianListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getTechnicianList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    technicianList = list,
                                )
                                technicianListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionDetailsState.value = _inspectionDetailsState.value.copy(
                                    technicianList = list,
                                )
                                technicianListIsLoading = true
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.ERROR -> {
                            technicianListIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
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
        data class ShowSnackBar(val messege: UiText): UiEvent()
        data class UpdateTextFields(val inspection: Inspection): UiEvent()
        data class NavigateTo(val route: String): UiEvent()
        data class SetFieldsIsEditable(val value: Boolean): UiEvent()
    }
}