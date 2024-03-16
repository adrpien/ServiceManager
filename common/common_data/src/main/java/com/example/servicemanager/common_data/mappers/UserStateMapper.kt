package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.UserTypeEntity
import com.example.servicemanager.common_domain.model.UserType


fun UserType.toUserTypeEntity(): UserTypeEntity {
    return UserTypeEntity(
        userTypeName = userTypeName,
        userTypeId = userTypeId,
        hospitals = hospitals
    )
}


