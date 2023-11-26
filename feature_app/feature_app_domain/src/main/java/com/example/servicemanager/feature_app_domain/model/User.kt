package com.example.servicemanager.feature_app_domain.model

import com.example.servicemanager.feature_app_domain.util.UserType


data class User (
    val userId: String = "0",
    val userName: String = "",
    val userType: UserType = UserType.technicianUserType()
)
