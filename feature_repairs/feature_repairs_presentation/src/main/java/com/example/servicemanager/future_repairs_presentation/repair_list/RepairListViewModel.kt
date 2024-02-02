package com.example.servicemanager.future_repairs_presentation.repair_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
    private val savedStateHandle: SavedStateHandle,
    private val repairsUseCases: RepairUseCases,
    private val appUseCases: AppUseCases
): ViewModel() {

    private var repairListIsLoading = true
    private var hospitalListIsLoading = true
    private var estStateListIsLoading = true
    private var technicianListIsLoading = true
    private var repairStateListIsLoading = true
    private var userTypeListIsLoading = true
    private var userIsLoading = true
    private var userTypeIsLoading = true

    private lateinit var currentUserId: String


    private var searchJob: Job? = null

    private var getRepairListJob: Job? = null

    val _repairListState = MutableStateFlow(RepairListState())
    val repairListState: StateFlow<RepairListState> = _repairListState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    companion object {
        private const val FETCH_DELAY = 200L
    }

    init {
        fetchHospitalList()
        fetchTechnicianList()
        fetchRepairStateList()
        fetchEstStateList()
        fetchUserTypeList()
        fetchUser()
        setUserType()
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
                    hospitalFilter = if(event.hospital.hospitalId != "0") repairListState.value.hospital else null
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
                var trigger = true
                while (trigger) {
                    if (
                        !userTypeListIsLoading &&
                        !userIsLoading &&
                        !userTypeIsLoading
                        ) {
                        repairsUseCases.getRepairList(
                            searchQuery = searchQuery,
                            fetchFromApi = fetchFromApi,
                            repairOrderType = repairOrderType,
                            hospitalFilter = hospitalFilter,
                            accessedHospitalIdList = repairListState.value.userType.hospitals
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
                        trigger = false
                    } else {
                        delay(FETCH_DELAY)
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
            !repairStateListIsLoading &&
            !userIsLoading &&
            !userTypeListIsLoading &&
            !userTypeIsLoading

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

    private fun fetchUser() {
        currentUserId = savedStateHandle.get<String>("userId") ?: "0"
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getUser(currentUserId).collect { result ->
                withContext(Dispatchers.Main){
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let { user ->
                                _repairListState.value = _repairListState.value.copy( user = user )
                            }
                            userIsLoading = false
                            setIsLoadingStatus()
                        }
                        ResourceState.LOADING -> {
                            result.data?.let { user ->
                                _repairListState.value = _repairListState.value.copy( user = user )
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

    private fun fetchUserTypeList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getUserTypeList().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result.resourceState) {
                        ResourceState.SUCCESS -> {
                            result.data?.let {
                                _repairListState.value =
                                    _repairListState.value.copy(userTypeList = it)
                            }
                            userTypeListIsLoading = false
                            setIsLoadingStatus()
                        }

                        ResourceState.LOADING -> {
                            result.data?.let {
                                _repairListState.value =
                                    _repairListState.value.copy(userTypeList = it)

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

    private fun setUserType() {
        viewModelScope.launch {
            var trigger = true
            while (trigger){
                if (userTypeListIsLoading == false && userIsLoading == false){
                    val userType = repairListState.value.userTypeList.find { it.userTypeId == repairListState.value.user.userType }
                    userType?.let {
                        _repairListState.value = _repairListState.value.copy(userType = userType)
                    }
                    trigger = false
                    userTypeIsLoading = false
                    setIsLoadingStatus()
                } else {
                    delay(FETCH_DELAY)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: UiText): UiEvent()
    }
}