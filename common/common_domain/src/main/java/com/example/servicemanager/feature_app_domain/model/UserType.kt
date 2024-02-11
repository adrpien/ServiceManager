package com.example.servicemanager.feature_app_domain.model


data class UserType (
    val userTypeId: String = "",
    val userTypeName: String = "",
    val hospitals: List<String> = emptyList()
) {
}