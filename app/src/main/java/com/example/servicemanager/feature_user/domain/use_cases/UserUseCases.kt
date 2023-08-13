package com.example.servicemanager.feature_user.domain.use_cases

data class UserUseCases(
    val getUser: GetUser,
    val getCurrentUser: GetCurrentUser,
    val authenticate: Authenticate
)
