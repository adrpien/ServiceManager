package com.example.servicemanager.feature_inspections_presentation.inspection_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections_domain.use_cases.InspectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectionListViewModel @Inject constructor(
    private val inspectionsUseCases: InspectionUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var inspectionListIsLoading = true
    private var hospitalListIsLoading = true
    private var estStateListIsLoading = true
    private var technicianListIsLoading = true
    private var inspectionStateListIsLoading = true

    private var searchJob: Job? = null

    private var getInspectionListJob: Job? = null

    val _inspectionListState = mutableStateOf(InspectionListState())
    val inspectionListState: State<InspectionListState> = _inspectionListState



    init {
        fetchHospitalList()
        fetchTechnicianList()
        fetchInspectionStateList()
        fetchEstStateList()
        fetchInspectionList(fetchFromApi = true)
    }

    fun onEvent(event: InspectionListEvent) {
        when(event) {
            is InspectionListEvent.onSearchQueryChange -> {
                _inspectionListState.value = _inspectionListState.value.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    launch {
                        delay(500L)
                        fetchInspectionList(
                            searchQuery = event.searchQuery,
                            fetchFromApi = false,
                            hospitalFilter = inspectionListState.value.hospital,
                            inspectionOrderType = inspectionListState.value.inspectionOrderType,
                        )
                    }
                }
            }
            is InspectionListEvent.Refresh -> {
                fetchInspectionList(
                    fetchFromApi = true,
                    inspectionOrderType = inspectionListState.value.inspectionOrderType,
                    searchQuery = inspectionListState.value.searchQuery,
                    hospitalFilter = inspectionListState.value.hospital
                )
            }
            is InspectionListEvent.orderInspectionList -> {
                _inspectionListState.value = _inspectionListState.value.copy(
                    inspectionOrderType = event.inspectionOrderType
                )
                fetchInspectionList(
                    fetchFromApi = false,
                    inspectionOrderType = event.inspectionOrderType,
                    searchQuery = inspectionListState.value.searchQuery,
                    hospitalFilter = inspectionListState.value.hospital
                )
            }

            is InspectionListEvent.ToggleSortSectionVisibility -> {
                _inspectionListState.value = _inspectionListState.value.copy(
                    isSortSectionVisible = !_inspectionListState.value.isSortSectionVisible
                )
                if(_inspectionListState.value.isHospitalFilterSectionVisible) {
                    _inspectionListState.value = _inspectionListState.value.copy(
                        isHospitalFilterSectionVisible = false
                    )
                }
            }

            is InspectionListEvent.ToggleOrderMonotonicity -> {
                _inspectionListState.value = _inspectionListState.value.copy(
                    inspectionOrderType = event.inspectionOrderType
                )
                fetchInspectionList(
                    fetchFromApi = false,
                    inspectionOrderType = event.inspectionOrderType,
                    searchQuery = inspectionListState.value.searchQuery,
                    hospitalFilter = inspectionListState.value.hospital
                )
            }
            is InspectionListEvent.ToggleHospitalFilterSectionVisibility -> {
                _inspectionListState.value = _inspectionListState.value.copy(
                    isHospitalFilterSectionVisible = !_inspectionListState.value.isHospitalFilterSectionVisible
                )
                if(_inspectionListState.value.isSortSectionVisible) {
                    _inspectionListState.value = _inspectionListState.value.copy(
                        isSortSectionVisible = false)
                }
            }
            is InspectionListEvent.filterInspectionListByHospital -> {
                _inspectionListState.value = _inspectionListState.value.copy(
                    hospital = event.hospital
                )
                fetchInspectionList(
                    fetchFromApi = false,
                    inspectionOrderType = inspectionListState.value.inspectionOrderType,
                    searchQuery = inspectionListState.value.searchQuery,
                    hospitalFilter = inspectionListState.value.hospital
                )
            }
        }
    }


    private fun fetchInspectionList(
        searchQuery: String = _inspectionListState.value.searchQuery.lowercase(),
        fetchFromApi: Boolean = false,
        inspectionOrderType: InspectionOrderType = _inspectionListState.value.inspectionOrderType,
        hospitalFilter: Hospital? = null
    ) {
            inspectionListIsLoading = true
            setIsLoadingStatus()
            viewModelScope.launch(Dispatchers.IO) {
            inspectionsUseCases.getInspectionList(
                searchQuery = searchQuery,
                fetchFromApi = fetchFromApi,
                inspectionOrderType = inspectionOrderType,
                hospitalFilter = hospitalFilter
            ).collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _inspectionListState.value = _inspectionListState.value.copy(
                                inspectionList = list
                            )
                            inspectionListIsLoading = false
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
                            _inspectionListState.value = _inspectionListState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getInspectionStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _inspectionListState.value = _inspectionListState.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getEstStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _inspectionListState.value = _inspectionListState.value.copy(
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
                            _inspectionListState.value = _inspectionListState.value.copy(
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
            !inspectionListIsLoading &&
            !hospitalListIsLoading &&
            !estStateListIsLoading &&
            !technicianListIsLoading &&
            !inspectionStateListIsLoading
        ){
            _inspectionListState.value = _inspectionListState.value.copy(
                isLoading = false
            )
        }
        else {
            _inspectionListState.value = _inspectionListState.value.copy(
                isLoading = true
            )
        }
    }

    sealed class UIEvent() {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}