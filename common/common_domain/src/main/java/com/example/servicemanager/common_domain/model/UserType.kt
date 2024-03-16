package com.example.servicemanager.common_domain.model


data class UserType (
    val userTypeId: String = "",
    val userTypeName: String = "",
    val hospitals: List<String> = emptyList()
) {
}