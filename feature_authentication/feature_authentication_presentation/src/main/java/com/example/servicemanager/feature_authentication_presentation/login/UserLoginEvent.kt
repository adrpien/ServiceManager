package com.example.servicemanager.feature_authentication_presentation.login

sealed  class UserLoginEvent {
    data class Authenticate(val mail: String, val password: String): UserLoginEvent()
    data class UpdateState(val mail: String, val password: String): UserLoginEvent()
    object GetCurrentUser: UserLoginEvent()
}
