package com.example.servicemanager.feature_authentication.domain.use_cases

data class UserUseCases(
    val getUser: GetUser,
    val getCurrentUser: GetCurrentUser,
    val authenticate: Authenticate
)
