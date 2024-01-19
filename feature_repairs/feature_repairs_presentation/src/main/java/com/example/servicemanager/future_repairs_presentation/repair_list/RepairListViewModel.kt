package com.example.servicemanager.future_repairs_presentation.repair_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_presentation.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType
import com.example.servicemanager.feature_repairs_domain.use_cases.RepairUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepairListViewModel @Inject constructor(
    private val repairsUseCases: RepairUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var repairListIsLoading = true
    private var hospitalListIsLoading = true
    private var estStateListIsLoading = true
    private var technicianListIsLoading = true
    private var repairStateListIsLoading = true

    private var searchJob: Job? = null

    private var getRepairListJob: Job? = null

    val _repairListState = MutableStateFlow(RepairListState())
    val repairListState: StateFlow<RepairListState> = _repairListState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchHospitalList()
        fetchTechnicianList()
        fetchRepairStateList()
        fetchEstStateList()
        fetchRepairList(fetchFromApi = true)
    }

    fun onEvent(event: RepairListEvent) {
        when(event) {
            is RepairListEvent.OnSearchQueryChange -> {
                _repairListState.value = _repairListState.value.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    launch {
                        delay(500L)
                        fetchRepairList(
                            searchQuery = event.searchQuery,
                            fetchFromApi = false,
                            hospitalFilter = repairListState.value.hospital,
                            repairOrderType = repairListState.value.repairOrderType,
                        )
                    }
                }
            }
            is RepairListEvent.Refresh -> {
                fetchRepairList(
                    fetchFromApi = true,
                    repairOrderType = repairListState.value.repairOrderType,
                    searchQuery = repairListState.value.searchQuery,
                    hospitalFilter = repairListState.value.hospital
                )
            }
            is RepairListEvent.OrderRepairList -> {
                _repairListState.value = _repairListState.value.copy(
                    repairOrderType = event.repairOrderType
                )
                fetchRepairList(
                    fetchFromApi = false,
                    repairOrderType = event.repairOrderType,
                    searchQuery = repairListState.value.searchQuery,
                    hospitalFilter = repairListState.value.hospital
                )
            }

            is RepairListEvent.ToggleSortSectionVisibility -> {
                if(_repairListState.value.isHospitalFilterSectionVisible) {
                    _repairListState.value = _repairListState.value.copy(
                        isHospitalFilterSectionVisible = false
                    )
                }
                _repairListState.value = _repairListState.value.copy(
                    isSortSectionVisible = !_repairListState.value.isSortSectionVisible
                )
            }

            is RepairListEvent.ToggleOrderMonotonicity -> {
                _repairListState.value = _repairListState.value.copy(
                    repairOrderType = event.repairOrderType
                )
                fetchRepairList(
                    fetchFromApi = false,
                    repairOrderType = event.repairOrderType,
                    searchQuery = repairListState.value.searchQuery,
                    hospitalFilter = repairListState.value.hospital
                )
            }
            is RepairListEvent.ToggleHospitalFilterSectionVisibility -> {
                if(_repairListState.value.isSortSectionVisible) {
                    _repairListState.value = _repairListState.value.copy(
                        isSortSectionVisible = false)
                }
                _repairListState.value = _repairListState.value.copy(
                    isHospitalFilterSectionVisible = !_repairListState.value.isHospitalFilterSectionVisible
                )
            }
            is RepairListEvent.FilterRepairListByHospital -> {
                _repairListState.value = _repairListState.value.copy(
                    hospital = event.hospital
                )
                fetchRepairList(
                    fetchFromApi = false,
                    repairOrderType = repairListState.value.repairOrderType,
                    searchQuery = repairListState.value.searchQuery,
                    hospitalFilter = repairListState.value.hospital
                )
            }

            is RepairListEvent.CopyToClipboard -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appUseCases.copyToClipboard(
                        string = event.string,
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.copied_to_clipboard)))
                }
            }
        }
    }


    private fun fetchRepairList(
        searchQuery: String = _repairListState.value.searchQuery.lowercase(),
        fetchFromApi: Boolean = false,
        repairOrderType: RepairOrderType = _repairListState.value.repairOrderType,
        hospitalFilter: Hospital? = null
    ) {
            repairListIsLoading = true
            setIsLoadingStatus()
            viewModelScope.launch(Dispatchers.IO) {
            repairsUseCases.getRepairList(
                searchQuery = searchQuery,
                fetchFromApi = fetchFromApi,
                repairOrderType = repairOrderType,
                hospitalFilter = hospitalFilter
            ).collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    repairList = list
                                )
                                repairListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    repairList = list
                                )
                            }
                            repairListIsLoading = true
                            setIsLoadingStatus()
                        }

                        ResourceState.ERROR -> {
                            repairListIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
                }
            }
        }
    }

    private fun fetchHospitalList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getHospitalList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    hospitalList = list,
                                )
                                hospitalListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    hospitalList = list,
                                )
                            }
                            hospitalListIsLoading = true
                            setIsLoadingStatus()
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getRepairStateList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    repairStateList = list,
                                )
                                repairStateListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getEstStateList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    estStateList = list,
                                )
                                estStateListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getTechnicianList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    technicianList = list,
                                )
                                technicianListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _repairListState.value = _repairListState.value.copy(
                                    technicianList = list,
                                )
                            }
                            technicianListIsLoading = true
                            setIsLoadingStatus()
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
            !repairListIsLoading &&
            !hospitalListIsLoading &&
            !estStateListIsLoading &&
            !technicianListIsLoading &&
            !repairStateListIsLoading
        ){
            _repairListState.value = _repairListState.value.copy(
                isLoading = false
            )
        }
        else {
            _repairListState.value = _repairListState.value.copy(
                isLoading = true
            )
        }
    }

    // TODO Fetching user to implement
    // xTODO User limitations to implement


    sealed class UiEvent {
        data class ShowSnackbar(val message: UiText): UiEvent()
    }
}