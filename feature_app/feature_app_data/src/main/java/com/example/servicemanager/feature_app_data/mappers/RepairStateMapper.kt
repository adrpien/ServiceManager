package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.RepairStateEntity

fun RepairState.toRepairStateEntity(): RepairStateEntity {
    return  RepairStateEntity(
        repairStateId = repairStateId,
        repairState = repairState
    )
}
