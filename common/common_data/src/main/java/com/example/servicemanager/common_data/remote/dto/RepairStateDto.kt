package com.example.servicemanager.common_data.remote.dto

import com.example.servicemanager.common_data.local.room.entities.RepairStateEntity

data class RepairStateDto (
    val repairStateId: String,
    val state: String = ""
        ) {

    fun toRepairStateEntity(): RepairStateEntity {
        return RepairStateEntity(
            repairStateId = repairStateId,
            repairState = state
        )
    }

    fun toRepairStateDto(): RepairStateDto {
        return RepairStateDto(
            repairStateId = repairStateId,
            state = state
        )
    }
}