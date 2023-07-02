package com.example.servicemanager.feature_inspections.presentation.inspection_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_app.domain.use_cases.devices.GetDeviceList
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
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

    private var searchJob: Job? = null

    var state = mutableStateOf(InspectionListState())

    init {
        fetchDeviceList()
        fetchHospitalList()
        fetchTechnicianList()
        fetchInspectionStateList()
        fetchEstStateList()
        fetchInspectionList(fetchFromApi = true)
    }

    fun onEvent(event: InspectionListEvent) {
        when(event) {
            is InspectionListEvent.onSearchQueryChange -> {
                state.value = state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    launch {
                        delay(500L)
                        fetchInspectionList(
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
        if(fetchFromApi) {
            viewModelScope.launch(Dispatchers.IO) {
                inspectionsUseCases.getInspectionList(searchQuery).collect { result ->
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                state.value = state.value.copy(inspectionList = list)
                                setIsLoadingStatus()
                            }
                        }
                        ResourceState.LOADING -> Unit
                        ResourceState.ERROR -> Unit
                    }
                }
            }
        } else {
            // TODO fetchInspectionList to implement in InspectionListViewModel
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
                            setIsLoadingStatus()
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }
    }

    private fun fetchDeviceList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getDeviceList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            state.value = state.value.copy(
                                deviceList = list,
                            )
                            setIsLoadingStatus()

                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }    }

    private fun fetchInspectionStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getInspectionStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            state.value = state.value.copy(
                                inspectionStateList = list,
                            )
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
            state.value.inspectionList.isNotEmpty() &&
            state.value.hospitalList.isNotEmpty() &&
            state.value.estStateList.isNotEmpty() &&
            state.value.deviceList.isNotEmpty() &&
            state.value.technicianList.isNotEmpty() &&
            state.value.inspectionStateList.isNotEmpty()
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
}