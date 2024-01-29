package com.example.servicemanager.feature_inspections_presentation.inspection_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_presentation.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
import com.example.servicemanager.feature_inspections_domain.use_cases.InspectionUseCases
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
class InspectionListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val inspectionsUseCases: InspectionUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var inspectionListIsLoading = true
    private var hospitalListIsLoading = true
    private var estStateListIsLoading = true
    private var technicianListIsLoading = true
    private var inspectionStateListIsLoading = true
    private var userTypeListIsLoading = true
    private var userIsLoading = true

    private var searchJob: Job? = null

    private val _inspectionListState = MutableStateFlow(InspectionListState())
    val inspectionListState: StateFlow<InspectionListState> = _inspectionListState

    private lateinit var currentUserId: String

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        fetchUserTypeList()
        fetchUser()
        setUserType()
        fetchHospitalList()
        fetchTechnicianList()
        fetchInspectionStateList()
        fetchEstStateList()
        fetchInspectionList(fetchFromApi = true)
    }

    private fun setUserType() {
        viewModelScope.launch {
            var trigger = true
            while (trigger){
                if (userTypeListIsLoading == false && userIsLoading == false){
                    val userType = inspectionListState.value.userTypeList.find { it.userTypeId == inspectionListState.value.user.userType }
                    userType?.let {
                        _inspectionListState.value = _inspectionListState.value.copy(userType = userType)
                    }
                    trigger = false
                } else {
                    delay(200)
                }
            }
        }
    }

    fun onEvent(event: InspectionListEvent) {
        when(event) {
            is InspectionListEvent.OnSearchQueryChange -> {
                _inspectionListState.value = _inspectionListState.value.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
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
            is InspectionListEvent.OrderInspectionList -> {
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
            is InspectionListEvent.FilterInspectionListByHospital -> {
                _inspectionListState.value = _inspectionListState.value.copy(
                    hospital = event.hospital
                )
                fetchInspectionList(
                    fetchFromApi = false,
                    inspectionOrderType = inspectionListState.value.inspectionOrderType,
                    searchQuery = inspectionListState.value.searchQuery,
                    hospitalFilter = if(event.hospital.hospitalId != "0") inspectionListState.value.hospital else null
                )
            }
            is InspectionListEvent.CopyToClipboard -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appUseCases.copyToClipboard(
                        string = event.string,
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.string_copied_to_clipboard)))
                }
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
                var trigger = true
                while (trigger) {
                    if (userTypeListIsLoading == false && userIsLoading == false) {
                        inspectionsUseCases.getInspectionList(
                            searchQuery = searchQuery,
                            fetchFromApi = fetchFromApi,
                            inspectionOrderType = inspectionOrderType,
                            hospitalFilter = hospitalFilter,
                            accessedHospitalIdList = inspectionListState.value.userType.hospitals
                        ).collect { result ->
                            withContext(Dispatchers.Main) {
                                when (result.resourceState) {
                                    ResourceState.SUCCESS -> {
                                        result.data?.let { list ->
                                            _inspectionListState.value =
                                                _inspectionListState.value.copy(
                                                    inspectionList = list
                                                )
                                            inspectionListIsLoading = false
                                            setIsLoadingStatus()
                                        }
                                    }

                                    ResourceState.LOADING -> {
                                        result.data?.let { list ->
                                            _inspectionListState.value =
                                                _inspectionListState.value.copy(
                                                    inspectionList = list
                                                )
                                        }
                                        inspectionListIsLoading = true
                                        setIsLoadingStatus()
                                    }

                                    ResourceState.ERROR -> {
                                        inspectionListIsLoading = false
                                        setIsLoadingStatus()
                                    }
                                }
                            }
                        }
                        trigger = false
                    } else {
                        delay(200)
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
                                _inspectionListState.value = _inspectionListState.value.copy(
                                    hospitalList = list,
                                )
                                hospitalListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionListState.value = _inspectionListState.value.copy(
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

    private fun fetchInspectionStateList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getInspectionStateList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { list ->
                                _inspectionListState.value = _inspectionListState.value.copy(
                                    inspectionStateList = list,
                                )
                                inspectionStateListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionListState.value = _inspectionListState.value.copy(
                                    inspectionStateList = list,
                                )
                            }
                            inspectionStateListIsLoading = true
                            setIsLoadingStatus()
                        }

                        ResourceState.ERROR -> {
                            inspectionStateListIsLoading = false
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
                                _inspectionListState.value = _inspectionListState.value.copy(
                                    estStateList = list,
                                )
                                estStateListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionListState.value = _inspectionListState.value.copy(
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
                                _inspectionListState.value = _inspectionListState.value.copy(
                                    technicianList = list,
                                )
                                technicianListIsLoading = false
                                setIsLoadingStatus()
                            }
                        }

                        ResourceState.LOADING -> {
                            result.data?.let { list ->
                                _inspectionListState.value = _inspectionListState.value.copy(
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

    private fun fetchUserTypeList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getUserTypeList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let {
                                _inspectionListState.value =
                                    _inspectionListState.value.copy(userTypeList = it)
                            }
                            userTypeListIsLoading = false
                            setIsLoadingStatus()
                        }

                        ResourceState.LOADING -> {
                            result.data?.let {
                                _inspectionListState.value =
                                    _inspectionListState.value.copy(userTypeList = it)

                            }
                            userTypeListIsLoading = true
                            setIsLoadingStatus()
                        }

                        ResourceState.ERROR -> {
                            userTypeListIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
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
            !inspectionStateListIsLoading &&
            !userTypeListIsLoading &&
            !userIsLoading
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

    private fun fetchUser() {
        currentUserId = savedStateHandle.get<String>("userId") ?: "0"
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getUser(currentUserId).collect { result ->
                withContext(Dispatchers.Main){
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { user ->
                                _inspectionListState.value = _inspectionListState.value.copy(user = user )
                            }
                            userIsLoading = false
                            setIsLoadingStatus()
                        }
                        ResourceState.LOADING -> {
                            result.data?.let { user ->
                                _inspectionListState.value = _inspectionListState.value.copy(user = user )
                            }
                            userIsLoading = true
                            setIsLoadingStatus()
                        }
                        ResourceState.ERROR -> {
                            userIsLoading = false
                            setIsLoadingStatus()
                        }
                    }
                }
            }
        }
    }
}
sealed class UiEvent {
    data class ShowSnackbar(val message: UiText): UiEvent()
}