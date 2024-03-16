package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.RepairStateEntity
import com.example.servicemanager.common_domain.model.RepairState

fun RepairState.toRepairStateEntity(): RepairStateEntity {
    return  RepairStateEntity(
        repairStateId = repairStateId,
        repairState = repairState
    )
}
