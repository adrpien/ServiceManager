package com.example.servicemanager.feature_authentication_presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_authentication_presentation.R
import com.example.servicemanager.feature_authentication_domain.use_cases.AuthenticationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases,
): ViewModel() {

    private var _userLoginState = mutableStateOf(UserLoginState())
    val userLoginState: State<UserLoginState> = _userLoginState

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(userLoginEvent: UserLoginEvent) {
        when(userLoginEvent){
            is UserLoginEvent.Authenticate -> {
                viewModelScope.launch(Dispatchers.IO) {
                    authenticationUseCases.authenticate(userLoginEvent.mail, userLoginEvent.password).collect { result ->
                        when(result.resourceState) {
                            ResourceState.LOADING -> Unit
                            ResourceState.ERROR -> {
                                    _eventFlow.emit(
                                        UiEvent.ShowSnackbar(
                                            messege = result.message ?: UiText.StringResource(R.string.unknown_error)
                                        )
                                        )
                            }
                            ResourceState.SUCCESS -> {
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
                    val result = authenticationUseCases.getCurrentUser()
                    when(result.resourceState) {
                        ResourceState.SUCCESS -> {
                            _userLoginState.value = userLoginState.value.copy(userId = result.data ?: "0")
                            _eventFlow.emit(UiEvent.Authenticate(result.data ?: "0"))
                        }
                        ResourceState.ERROR -> Unit
                        ResourceState.LOADING -> Unit
                    }
                }
            }
        }
    }
}

sealed class UiEvent {
    data class Authenticate(val userId: String): UiEvent()
    data class ShowSnackbar(val messege: UiText): UiEvent()
}