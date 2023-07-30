package com.example.servicemanager.feature_user.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserDto(
    val userId: String,
    val userName: String,
    val userType: String
)
