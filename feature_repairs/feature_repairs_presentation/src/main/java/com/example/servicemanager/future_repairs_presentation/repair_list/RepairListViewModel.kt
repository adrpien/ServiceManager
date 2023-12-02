package com.example.servicemanager.future_repairs_presentation.repair_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType
import com.example.servicemanager.feature_repairs_domain.use_cases.RepairUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    val _repairListState = mutableStateOf(RepairListState())
    val repairListState: State<RepairListState> = _repairListState



    init {
        fetchHospitalList()
        fetchTechnicianList()
        fetchRepairStateList()
        fetchEstStateList()
        fetchRepairList(fetchFromApi = true)
    }

    fun onEvent(event: RepairListEvent) {
        when(event) {
            is RepairListEvent.onSearchQueryChange -> {
                _repairListState.value = _repairListState.value.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
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
            is RepairListEvent.orderRepairList -> {
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
            is RepairListEvent.filterRepairListByHospital -> {
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
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _repairListState.value = _repairListState.value.copy(
                                repairList = list
                            )
                            repairListIsLoading = false
                            setIsLoadingStatus()
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
                            _repairListState.value = _repairListState.value.copy(
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

    private fun fetchRepairStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getRepairStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _repairListState.value = _repairListState.value.copy(
                                repairStateList = list,
                            )
                            repairStateListIsLoading = false
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getEstStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _repairListState.value = _repairListState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getTechnicianList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _repairListState.value = _repairListState.value.copy(
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

    sealed class UIEvent() {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}