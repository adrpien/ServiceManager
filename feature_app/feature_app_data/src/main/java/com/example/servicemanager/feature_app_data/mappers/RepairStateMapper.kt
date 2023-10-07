package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.entities.RepairStateEntity
import com.example.servicemanager.feature_app_domain.model.RepairState

fun RepairState.toRepairStateEntity(): RepairStateEntity {
    return  RepairStateEntity(
        repairStateId = repairStateId,
        repairState = repairState
    )
}
