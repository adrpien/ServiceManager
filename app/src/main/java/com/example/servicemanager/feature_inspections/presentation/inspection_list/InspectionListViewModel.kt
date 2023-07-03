package com.example.servicemanager.feature_inspections.presentation.inspection_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    val _inspectionListstate = mutableStateOf(InspectionListState())
    val inspectionListstate: State<InspectionListState> = _inspectionListstate

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
                _inspectionListstate.value = _inspectionListstate.value.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    launch {
                        delay(500L)
                        fetchInspectionList(
                            searchQuery = event.searchQuery,
                            fetchFromApi = false
                        )
                    }
                }
            }
            is InspectionListEvent.Refresh -> {
                fetchInspectionList(
                    fetchFromApi = true
                )
            }
            is InspectionListEvent.orderInspectionList -> {

                // TODO orderInspectionList in InspectionListViewModel
                fetchInspectionList(
                    fetchFromApi = false
                )

                _inspectionListstate.value = _inspectionListstate.value.copy(
                    orderType = event.orderType
                )
            }

            is InspectionListEvent.ToggleSortSectionVisibility -> {
                _inspectionListstate.value = _inspectionListstate.value.copy(
                    isSortSectionVisible = !_inspectionListstate.value.isSortSectionVisible
                )
            }
        }
    }


    private fun fetchInspectionList(
        searchQuery: String = _inspectionListstate.value.searchQuery.lowercase(),
        fetchFromApi: Boolean = false
    ) {
            inspectionListIsLoading = true
            setIsLoadingStatus()
            viewModelScope.launch(Dispatchers.IO) {
            inspectionsUseCases.getInspectionList(
                searchQuery = searchQuery,
                fetchFromApi = fetchFromApi
            ).collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _inspectionListstate.value = _inspectionListstate.value.copy(inspectionList = list)
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
                            _inspectionListstate.value = _inspectionListstate.value.copy(
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
                            _inspectionListstate.value = _inspectionListstate.value.copy(
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
                            _inspectionListstate.value = _inspectionListstate.value.copy(
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
                            _inspectionListstate.value = _inspectionListstate.value.copy(
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
            _inspectionListstate.value = _inspectionListstate.value.copy(
                isLoading = false
            )
        }
        else {
            _inspectionListstate.value = _inspectionListstate.value.copy(
                isLoading = true
            )
        }
    }

    sealed class UIEvent() {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}