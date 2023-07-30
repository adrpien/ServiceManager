package com.example.servicemanager.feature_user.domain.model

import androidx.room.Entity


data class User(
    val userId: String,
    val userName: String,
    val userType: String
)
