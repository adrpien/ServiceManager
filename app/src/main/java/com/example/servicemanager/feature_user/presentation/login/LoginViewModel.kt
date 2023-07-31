package com.example.servicemanager.feature_user.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicemanager.feature_user.domain.use_cases.Authenticate
import com.example.servicemanager.feature_user.domain.use_cases.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authenticateUseCase: Authenticate,
    private val getUserUseCase: GetUser
): ViewModel() {

    private var _userLoginState = mutableStateOf(UserLoginState())
    val userLoginState: State<UserLoginState> = _userLoginState

    fun onEvent(userLoginEvent: UserLoginEvent) {
        when(userLoginEvent){
            is UserLoginEvent.Authenticate -> {
                viewModelScope.launch(Dispatchers.IO) {
                    authenticateUseCase(userLoginEvent.mail, userLoginEvent.mail)
                }
            }
            is UserLoginEvent.UpdateState -> {
                _userLoginState.value = _userLoginState.value.copy(
                    mail = userLoginEvent.mail,
                    password = userLoginEvent.password
                )
            }
        }
    }
}

sealed class UIEvent() {
    data class UpdateTextFields(val mail: String, val password: String): UIEvent()
    data class ShowSnackbar(val messege: String): UIEvent()
}