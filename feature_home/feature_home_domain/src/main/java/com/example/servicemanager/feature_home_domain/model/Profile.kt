package com.example.servicemanager.feature_home_domain.model

import com.example.servicemanager.common_domain.model.User

data class Profile(
    val user: User = User(),
    val profilePicture: ByteArray = byteArrayOf(),
    val pointsThisMonth: Int,
    )
