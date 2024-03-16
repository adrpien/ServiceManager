package com.example.feature_home_presentation.user_type_list_manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_home_presentation.R
import com.example.servicemanager.common_domain.model.Hospital
import com.example.servicemanager.common_domain.model.UserType
import com.example.servicemanager.common_domain.use_cases.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTypeListManagerViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
): ViewModel() {

    private val _userTypeListState = MutableStateFlow<List<UserType>>(emptyList())
    val userTypeListState: StateFlow<List<UserType>> = _userTypeListState

    private val _hospitalListState = MutableStateFlow<List<Hospital>>(emptyList())
    val hospitalListState: StateFlow<List<Hospital>> = _hospitalListState

    var lastDeleteUserType: UserType? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchUserTypeList()
        fetchHospitalList()
    }

    fun onEvent(userTypeManagerEvent: UserTypeManagerEvent) {
        when(userTypeManagerEvent) {
            is UserTypeManagerEvent.AddUserType -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.createUserType(userTypeManagerEvent.userType)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchUserTypeList()
                        }
                        ResourceState.LOADING -> Unit
                    }
                }

            }
            is UserTypeManagerEvent.ChangeOrder -> Unit
            is UserTypeManagerEvent.DeleteUserType -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.deleteUserType(userTypeManagerEvent.userType)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            _eventFlow.emit(UiEvent.ShowSnackBar(UiText.StringResource(R.string.revert_delete)))
                            lastDeleteUserType = userTypeManagerEvent.userType
                            fetchUserTypeList()
                        }
                        ResourceState.LOADING -> Unit
                    }
                }
            }
            is UserTypeManagerEvent.RevertUserType -> {
                 viewModelScope.launch(Dispatchers.IO) {
                     appUseCases.createUserTypeWithId(userTypeManagerEvent.userType)
                     lastDeleteUserType = null
                     fetchUserTypeList()

                 }
            }
            is UserTypeManagerEvent.UpdateUserType -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = appUseCases.updateUserType(userTypeManagerEvent.userType)
                    when(result.resourceState) {
                        ResourceState.ERROR -> Unit
                        ResourceState.SUCCESS -> {
                            fetchUserTypeList()
                        }
                        ResourceState.LOADING -> Unit
                    }
                }
            }
        }
    }


    private fun fetchUserTypeList() {
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getUserTypeList().collect { result ->
                when(result.resourceState) {
                    ResourceState.ERROR -> Unit
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _userTypeListState.value = list
                        }
                    }
                    ResourceState.LOADING -> Unit
                }
            }
        }
    }

    private fun fetchHospitalList(){
        viewModelScope.launch(Dispatchers.Main) {
            appUseCases.getHospitalList().collect { result ->
                when(result.resourceState) {
                    ResourceState.SUCCESS -> {
                        result.data?.let { list ->
                            _hospitalListState.value = list
                        }
                    }
                    ResourceState.ERROR -> Unit
                    ResourceState.LOADING -> Unit
                }
            }
        }
    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: UiText): UiEvent()
}

sealed class UserTypeManagerEvent {
    data class DeleteUserType(val userType: UserType): UserTypeManagerEvent()
    data class AddUserType(val userType: UserType): UserTypeManagerEvent()
    data class RevertUserType(val userType: UserType): UserTypeManagerEvent()
    data class ChangeOrder(val technicianList: List<UserType>): UserTypeManagerEvent()
    data class UpdateUserType(val userType: UserType): UserTypeManagerEvent()
}