package com.example.servicemanager.feature_inspections.presentation.inspection_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
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

    var state = mutableStateOf(InspectionListState())

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
                state.value = state.value.copy(searchQuery = event.searchQuery)
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
        }
    }


    private fun fetchInspectionList(
        searchQuery: String = state.value.searchQuery.lowercase(),
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
                            state.value = state.value.copy(inspectionList = list)
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
                            state.value = state.value.copy(
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
                            state.value = state.value.copy(
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
                            state.value = state.value.copy(
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
                            state.value = state.value.copy(
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

    sealed class UIEvent() {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}