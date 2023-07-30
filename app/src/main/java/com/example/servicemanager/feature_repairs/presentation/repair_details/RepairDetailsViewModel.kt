package com.example.servicemanager.feature_repairs.presentation.repair_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.core.util.Helper.Companion.convertToBitmap
import com.example.servicemanager.core.util.Helper.Companion.convertToByteArray
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.feature_repairs.domain.use_cases.RepairUseCases
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
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
            is RepairDetailsEvent.UpdateState -> {
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
                                    appUseCases.saveSignature(repairDetailsState.value.repair.repairId, convertToByteArray(repairDetailsState.value.signature)).collect()
                                }
                            }
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> Unit
                        }
                    }
                }
            }
            is RepairDetailsEvent.UpdateRepair -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repairUseCases.updateRepair(repairDetailsState.value.repair).collect()
                    viewModelScope.launch(Dispatchers.IO) {
                        appUseCases.updateSignature(repairDetailsState.value.repair.repairId, convertToByteArray(repairDetailsState.value.signature)).collect()
                    }
                }
            }
        }
    }

    private fun fetchRepair() {
        currentRepairId = savedStateHandle.get<String?>("repairId")
        if (currentRepairId != "0") {
            viewModelScope.launch(Dispatchers.Main) {
                repairUseCases
                    .getRepair(repairId = currentRepairId.toString())
                    .collect { result ->
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { repair ->
                                    _repairDetailsState.value =
                                        _repairDetailsState.value.copy(
                                            repair = repair
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
                    repair = Repair()
                )
        }
    }
    // TODO Bug needs to be fixed - fetches signature even if there no signature
    private fun fetchSignature() {
        if (currentRepairId != "0") {
            viewModelScope.launch(Dispatchers.Main) {
                appUseCases
                    .getSignature(currentRepairId.toString())
                    .collect { result ->
                        when (result.resourceState) {
                            ResourceState.SUCCESS -> {
                                result.data?.let { signature ->
                                    _repairDetailsState.value =
                                        _repairDetailsState.value.copy(
                                            signature = convertToBitmap(signature)
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
        viewModelScope.launch(Dispatchers.Main) {
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
        viewModelScope.launch(Dispatchers.Main) {
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
        viewModelScope.launch(Dispatchers.Main) {
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
            _repairDetailsState.value.repair.repairId.isNotEmpty() &&
            _repairDetailsState.value.hospitalList.isNotEmpty() &&
            _repairDetailsState.value.estStateList.isNotEmpty() &&
            _repairDetailsState.value.technicianList.isNotEmpty() &&
            _repairDetailsState.value.repairStateList.isNotEmpty()
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
    }
}