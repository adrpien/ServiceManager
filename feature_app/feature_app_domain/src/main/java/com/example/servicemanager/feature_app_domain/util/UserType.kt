package com.example.servicemanager.feature_app_domain.util

sealed class UserType(id: String) {
    data class technicianUserType(val id: String = "TECHNICIAN_USER_TYPE"): UserType(id)
    data class adminUserType(val id: String = "ADMIN_USER_TYPE"): UserType(id)
    data class godUserType(val id: String = "GOD_USER_TYPE"): UserType(id)
}
