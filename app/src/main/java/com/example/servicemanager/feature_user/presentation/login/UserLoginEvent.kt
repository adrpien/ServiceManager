package com.example.servicemanager.feature_user.presentation.login

sealed  class UserLoginEvent {
    data class Authenticate(val mail: String): UserLoginEvent()
    data class UpdateState(val mail: String, val password: String): UserLoginEvent()
}