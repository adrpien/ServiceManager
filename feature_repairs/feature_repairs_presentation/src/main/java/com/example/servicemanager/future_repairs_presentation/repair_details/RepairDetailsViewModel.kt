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
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.use_cases.RepairUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    private val _repairDetailsState = mutableStateOf(RepairDetailsState())
    val repairDetailsState: State<RepairDetailsState> = _repairDetailsState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchRepair()
        fetchSignature()
        fetchHospitalList()
        fetchEstStateList()
        fetchRepairStateList()
        fetchTechnicianList()
    }

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
                    repairUseCases.saveRepair(repairDetailsState.value.repair).collect() { result ->
                        when(result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { repairId ->
                                    _repairDetailsState.value = _repairDetailsState.value.copy(repair = _repairDetailsState.value.repair.copy(repairId = repairId))
                                }
                                viewModelScope.launch(Dispatchers.IO) {
                                    appUseCases.saveSignature(repairDetailsState.value.repair.repairId, bitmapToByteArray(repairDetailsState.value.signature)).collect()
                                }
                                _eventFlow.emit(
                                    UiEvent.NavigateTo(
                                        Screen.RepairListScreen.route
                                    )
                                )

                            }
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(result.data ?: "Uknown error"))
                            }
                        }
                    }
                }
            }
            is RepairDetailsEvent.UpdateRepair -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repairUseCases.updateRepair(repairDetailsState.value.repair).collect()
                }
                viewModelScope.launch(Dispatchers.IO) {
                    appUseCases.updateSignature(repairDetailsState.value.repair.repairId, bitmapToByteArray(repairDetailsState.value.signature)).collect()
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
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { repair ->
                                    repairIsLoading = false
                                    setIsLoadingStatus()
                                    _repairDetailsState.value =
                                        _repairDetailsState.value.copy(
                                            repair = repair,
                                        )
                                    _eventFlow.emit(UiEvent.UpdateTextFields(repair))
                                }
                            }
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> Unit
                        }
                    }
            }
        } else {
            _repairDetailsState.value =
                _repairDetailsState.value.copy(
                    repair = Repair(),
                    isInEditMode = true
                )
            repairIsLoading = false
            setIsLoadingStatus()
        }
    }
    // TODO Bug needs to be fixed - fetches signature even if there no signature
    private fun fetchSignature() {
        if (currentRepairId != "0") {
            viewModelScope.launch(Dispatchers.IO) {
                appUseCases
                    .getSignature(currentRepairId.toString())
                    .collect { result ->
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { signature ->
                                    _repairDetailsState.value =
                                        _repairDetailsState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            hospitalListIsLoading = true
            setIsLoadingStatus()
            appUseCases.getHospitalList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            hospitalListIsLoading = false
                            setIsLoadingStatus()
                            _repairDetailsState.value = _repairDetailsState.value.copy(
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
    private fun fetchRepairStateList() {
        repairStateListIsLoading = true
        setIsLoadingStatus()
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getRepairStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            repairStateListIsLoading = false
                            setIsLoadingStatus()
                            _repairDetailsState.value = _repairDetailsState.value.copy(
                                repairStateList = list,
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getEstStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            estStateListIsLoading = false
                            setIsLoadingStatus()
                            _repairDetailsState.value = _repairDetailsState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getTechnicianList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            technicianListIsLoading = false
                            setIsLoadingStatus()
                            _repairDetailsState.value = _repairDetailsState.value.copy(
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
        data class ShowSnackBar(val messege: String): UiEvent()

        data class UpdateTextFields(val text: Repair): UiEvent()
        data class NavigateTo(val route: String): UiEvent()

        data class SetFieldsIsEditable(val value: Boolean): UiEvent()

    }
}