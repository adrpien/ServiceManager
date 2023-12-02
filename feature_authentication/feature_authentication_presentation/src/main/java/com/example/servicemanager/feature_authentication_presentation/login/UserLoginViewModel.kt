package com.example.servicemanager.feature_authentication_presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.NavigationRoutes
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_authentication_domain.use_cases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
): ViewModel() {

    private var _userLoginState = mutableStateOf(UserLoginState())
    val userLoginState: State<UserLoginState> = _userLoginState

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(userLoginEvent: UserLoginEvent) {
        when(userLoginEvent){
            is UserLoginEvent.Authenticate -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userUseCases.authenticate(userLoginEvent.mail, userLoginEvent.password).collect() { result ->
                        when(result.resourceState) {
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> {
                                    _eventFlow.emit(
                                        UiEvent.ShowSnackbar(
                                            messege = result.message ?: "Unknown error"
                                        )
                                    )
                            }
                            ResourceState.SUCCESS -> {
                                if (result.data != null) {
                                    _userLoginState.value = _userLoginState.value.copy(
                                        userId = result.data.toString()
                                    )
                                }
                                _eventFlow.emit(UiEvent.Authenticate(_userLoginState.value.userId))
                            }
                        }
                    }
                }
            }
            is UserLoginEvent.UpdateState -> {
                _userLoginState.value = _userLoginState.value.copy(
                    mail = userLoginEvent.mail,
                    password = userLoginEvent.password
                )
            }
            is UserLoginEvent.GetCurrentUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userUseCases.getCurrentUser().collect { result ->
                        when(result.resourceState) {
                            ResourceState.SUCCESS -> {
                                _eventFlow.emit(UiEvent.Authenticate(result.data ?: "0"))
                            }
                            ResourceState.ERROR -> {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        messege = result.message ?: "Unknown error"
                                    )
                                )
                            }
                            ResourceState.LOADING -> Unit
                        }
                    }
                }
            }
        }
    }
    sealed class UiEvent() {
        data class Authenticate(val userId: String): UiEvent()
        data class ShowSnackbar(val messege: String): UiEvent()
    }
}

