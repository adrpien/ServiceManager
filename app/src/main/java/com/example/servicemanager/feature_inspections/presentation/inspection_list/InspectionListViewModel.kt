package com.example.servicemanager.feature_inspections.presentation.inspection_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_app.domain.use_cases.devices.GetDeviceList
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectionListViewModel @Inject constructor(
    private val getInspectionList: GetInspectionList,
    private val getTechnicianList: GetTechnicianList,
    private val getHospitalList: GetHospitalList,
    private val getEstStateList: GetEstStateList,
    private val getInspectionStateList: GetInspectionStateList,
    private val getDeviceList: GetDeviceList
): ViewModel() {

    private var searchJob: Job? = null

    var state = mutableStateOf(InspectionListState())

    init {
        fetchHospitalList()
        fetchTechnicianList()
        fetchInspectionStateList()
        fetchEstStateList()
        fetchDeviceList()
        fetchInspectionList()
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
                            searchQuery = event.query,
                            false
                        )
                    }
                }
            }
            is InspectionListEvent.Refresh -> {
                fetchInspectionList(
                    fetchFromApi = false
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
                getInspectionList(searchQuery).collect { result ->
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                setIsLoadingStatus()
                                state.value = state.value.copy(inspectionList = list)
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
            getHospitalList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(
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

    private fun fetchDeviceList() {
        viewModelScope.launch(Dispatchers.IO) {
            getDeviceList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(
                                deviceList = list,
                            )
                        }
                    }
                    ResourceState.LOADING -> Unit
                    ResourceState.ERROR -> Unit
                }
            }
        }    }

    private fun fetchInspectionStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            getInspectionStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(
                                inspectionStateList = list,
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
        viewModelScope.launch(Dispatchers.IO) {
            getEstStateList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(
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
        viewModelScope.launch(Dispatchers.IO) {
            getTechnicianList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            setIsLoadingStatus()
                            state.value = state.value.copy(
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