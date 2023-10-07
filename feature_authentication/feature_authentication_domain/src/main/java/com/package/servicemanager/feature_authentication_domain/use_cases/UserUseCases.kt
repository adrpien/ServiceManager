package com.example.servicemanager.feature_authentication_domain.use_cases

data class UserUseCases(
    val authenticate: Authenticate,
    val getCurrentUser: GetCurrentUser,
)
