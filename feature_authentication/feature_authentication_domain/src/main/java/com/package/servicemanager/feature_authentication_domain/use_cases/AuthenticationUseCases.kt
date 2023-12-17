package com.example.servicemanager.feature_authentication_domain.use_cases

import com.servicemanager.feature_authentication_domain.use_cases.LogOut

data class AuthenticationUseCases(
    val authenticate: Authenticate,
    val getCurrentUser: GetCurrentUser,
    val logOut: LogOut
)
