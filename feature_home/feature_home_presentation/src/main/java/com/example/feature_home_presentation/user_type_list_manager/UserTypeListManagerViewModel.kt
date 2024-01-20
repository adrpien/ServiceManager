package com.example.feature_home_presentation.user_type_list_manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_home_presentation.R
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_app_domain.use_cases.AppUseCases
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

    private val _userTypeState = MutableStateFlow(UserType())

    var lastDeleteUserType: UserType? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchUserTypeList()
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
                            fetchUserTypeList()
                            _eventFlow.emit(UiEvent.ShowSnackBar(UiText.StringResource(R.string.revert_delete)))
                            lastDeleteUserType = userTypeManagerEvent.userType
                        }
                        ResourceState.LOADING -> Unit
                    }
                }
            }
            is UserTypeManagerEvent.RevertUserType -> {
                 viewModelScope.launch(Dispatchers.IO) {
                     appUseCases.createUserTypeWithId(userTypeManagerEvent.userType)
                     lastDeleteUserType = null
                 }
            }
            is UserTypeManagerEvent.UpdateUserType -> {
                _userTypeState.value = userTypeManagerEvent.userType
            }
        }
    }


    private fun fetchUserTypeList() {
        var userTypeList: List<UserType>? = null
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