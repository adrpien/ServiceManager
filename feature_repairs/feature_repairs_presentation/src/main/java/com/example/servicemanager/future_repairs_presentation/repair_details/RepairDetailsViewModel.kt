package com.example.servicemanager.future_repairs_presentation.repair_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Helper.Companion.byteArrayToBitmap
import com.example.core.util.Helper.Companion.bitmapToByteArray
import com.example.core.util.ResourceState
import com.example.core.util.Screen
import com.example.core.util.UiText
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.use_cases.RepairUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepairDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repairUseCases: RepairUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var repairIsLoading = true
    private var hospitalListIsLoading = true
    private var estStateListIsLoading = true
    private var technicianListIsLoading = true
    private var repairStateListIsLoading = true

    private var currentRepairId: String? = null

    private val _repairDetailsState = MutableStateFlow(RepairDetailsState())
    val repairDetailsState: StateFlow<RepairDetailsState> = _repairDetailsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchRepair()
        fetchHospitalList()
        fetchEstStateList()
        fetchRepairStateList()
        fetchTechnicianList()
    }

    //  TODO Optimize Save and update record, app is very unresponsive
    fun onEvent(repairDetailsEvent: RepairDetailsEvent) {
        when(repairDetailsEvent) {
            is RepairDetailsEvent.UpdateRepairState -> {
                _repairDetailsState.value = _repairDetailsState.value.copy(
                    repair = repairDetailsEvent.repair
                )
            }
            is RepairDetailsEvent.UpdateSignatureState -> {
                _repairDetailsState.value = _repairDetailsState.value.copy(
                    signature = repairDetailsEvent.signature
                )
            }
            is RepairDetailsEvent.SaveRepair -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = repairUseCases.saveRepair(repairDetailsState.value.repair)
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { repairId ->
                                _repairDetailsState.value = _repairDetailsState.value.copy(repair = _repairDetailsState.value.repair.copy(repairId = repairId))
                            }
                            appUseCases.saveSignature(repairDetailsState.value.repair.repairId, bitmapToByteArray(repairDetailsState.value.signature))
                            _eventFlow.emit(UiEvent.NavigateTo(Screen.RepairListScreen.route))

                        }
                        ResourceState.LOADING -> Unit
                        ResourceState.ERROR -> {
                            if (result.data == "CONNECTION_ERROR") {
                                _eventFlow.emit(UiEvent.NavigateTo(Screen.RepairListScreen.route))
                                appUseCases.saveSignature(repairDetailsState.value.repair.signatureId, bitmapToByteArray(repairDetailsState.value.signature))
                            }
                            _eventFlow.emit(UiEvent.ShowSnackBar(result.message ?: UiText.DynamicString("Uknown error")))
                        }
                    }
                }
            }
            is RepairDetailsEvent.UpdateRepair -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val result = repairUseCases.updateRepair(repairDetailsState.value.repair)
                    when(result.resourceState) {
                        ResourceState.ERROR -> {
                            if (result.data == "CONNECTION_ERROR") {
                                _eventFlow.emit(UiEvent.NavigateTo(Screen.RepairListScreen.route))
                                appUseCases.updateSignature(repairDetailsState.value.repair.signatureId, bitmapToByteArray(repairDetailsState.value.signature))
                            }
                            _eventFlow.emit(UiEvent.ShowSnackBar(
                                result.message ?: UiText.DynamicString("Unknown error")
                            ))
                        }
                        ResourceState.SUCCESS -> {
                            result.data?.let { repairId ->
                                _repairDetailsState.value = _repairDetailsState.value.copy(repair = _repairDetailsState.value.repair.copy(repairId = repairId))
                                appUseCases.saveSignature(repairId, bitmapToByteArray(repairDetailsState.value.signature))
                            }
                            appUseCases.updateSignature(repairDetailsState.value.repair.repairId, bitmapToByteArray(repairDetailsState.value.signature))
                            _eventFlow.emit(UiEvent.NavigateTo(Screen.RepairListScreen.route))
                        }
                        ResourceState.LOADING -> Unit
                    }
                }
            }
            is RepairDetailsEvent.SetIsInEditMode -> {
                _repairDetailsState.value = _repairDetailsState.value.copy(
                    isInEditMode = repairDetailsEvent.value
                )
                viewModelScope.launch(Dispatchers.IO) {
                    _eventFlow.emit(UiEvent.SetFieldsIsEditable(repairDetailsState.value.isInEditMode))
                }
            }
        }
    }

    private fun fetchRepair() {
        repairIsLoading = true
        setIsLoadingStatus()
        currentRepairId = savedStateHandle.get<String?>("repairId")
        if (currentRepairId != "0") {
            viewModelScope.launch(Dispatchers.IO) {
                repairUseCases
                    .getRepair(repairId = currentRepairId.toString())
                    .collect { result ->
                        withContext(Dispatchers.Main) {
                            when (result.resourceState) {
                                ResourceState.SUCCESS -> {
                                    result.data?.let { repair ->
                                        _repairDetailsState.value =
                                            _repairDetailsState.value.copy(
                                                repair = repair,
                                            )
                                        _eventFlow.emit(UiEvent.UpdateTextFields(repair))
                                        fetchSignature(repair)
                                    }
                                    repairIsLoading = false
                                    setIsLoadingStatus()
                                }
                                ResourceState.LOADING -> {
                                    result.data?.let { repair ->
                                        _repairDetailsState.value =
                                            _repairDetailsState.value.copy(
                                                repair = repair,)
                                        _eventFlow.emit(UiEvent.UpdateTextFields(repair))
                                    }
                                    repairIsLoading = true
                                    setIsLoadingStatus()
                                }
                                ResourceState.ERROR -> Unit
                            }
                        }
                    }
            }
        } else {
            _repairDetailsState.value =
                _repairDetailsState.value.copy(
                    repair = Repair(repairId = "0"),
                    isInEditMode = true
                )
            repairIsLoading = false
            setIsLoadingStatus()
        }
    }
    private fun fetchSignature(repair: Repair) {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases
                .getSignature(repair.signatureId)
                .collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { signature ->
                                    _repairDetailsState.value =
                                        _repairDetailsState.value.copy(
                                            signature = byteArrayToBitmap(signature)
                                        )
                                }
                            }

                            ResourceState.LOADING -> {
                                result.data?.let { signature ->
                                    _repairDetailsState.value =
                                        _repairDetailsState.value.copy(
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
                                hospitalListIsLoading = false
                                setIsLoadingStatus()
                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    hospitalList = list,
                                )
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairDetailsState.value = _repairDetailsState.value.copy(
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
    private fun fetchRepairStateList() {
        repairStateListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getRepairStateList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                repairStateListIsLoading = false
                                setIsLoadingStatus()
                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    repairStateList = list,
                                )
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->

                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    repairStateList = list,
                                )
                            }
                            repairStateListIsLoading = true
                            setIsLoadingStatus()
                        }

                        ResourceState.ERROR -> {
                            repairStateListIsLoading = false
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
                                estStateListIsLoading = false
                                setIsLoadingStatus()
                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    estStateList = list,
                                )
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    estStateList = list,
                                )
                            }
                            estStateListIsLoading = true
                            setIsLoadingStatus()
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
                                technicianListIsLoading = false
                                setIsLoadingStatus()
                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    technicianList = list,
                                )
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                technicianListIsLoading = false
                                setIsLoadingStatus()
                                _repairDetailsState.value = _repairDetailsState.value.copy(
                                    technicianList = list,
                                )
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
    private fun setIsLoadingStatus() {
        if(
            !repairIsLoading &&
            !hospitalListIsLoading &&
            !estStateListIsLoading &&
            !technicianListIsLoading &&
            !repairStateListIsLoading
        ){
            _repairDetailsState.value = _repairDetailsState.value.copy(
                isLoading = false
            )
        }
        else {
            _repairDetailsState.value = _repairDetailsState.value.copy(
                isLoading = true
            )
        }
    }
    sealed class UiEvent {
        data class ShowSnackBar(val messege: UiText): UiEvent()

        data class UpdateTextFields(val text: Repair): UiEvent()
        data class NavigateTo(val route: String): UiEvent()

        data class SetFieldsIsEditable(val value: Boolean): UiEvent()

    }
}