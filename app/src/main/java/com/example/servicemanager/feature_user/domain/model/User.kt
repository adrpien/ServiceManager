package com.example.servicemanager.feature_user.domain.model

import androidx.room.Entity
import javax.inject.Inject


data class User (
    val userId: String = "0",
    val userName: String = "",
    val userType: String = ""
)
