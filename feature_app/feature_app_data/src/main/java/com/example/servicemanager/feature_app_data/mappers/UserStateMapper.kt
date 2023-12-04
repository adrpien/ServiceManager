package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.entities.UserTypeEntity
import com.example.servicemanager.feature_app_domain.model.UserType


fun UserType.toUserTypeEntity(): UserTypeEntity {
    return UserTypeEntity(
        userTypeName = userTypeName,
        userTypeId = userTypeId,
        hospitals = hospitals
    )
}


