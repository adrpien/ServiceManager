package com.example.servicemanager.feature_user.presentation.login

data class UserLoginState(
    val mail: String = "",
    val password: String = "",
    val userId: String = "0"
)
