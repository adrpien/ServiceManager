package com.example.servicemanager.feature_authentication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val userName: String,
    val userType: String
)
