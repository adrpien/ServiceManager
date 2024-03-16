package com.example.feature_home_presentation.home

import com.example.servicemanager.common_domain.model.User

data class HomeState(
    val user: User = User(),
)