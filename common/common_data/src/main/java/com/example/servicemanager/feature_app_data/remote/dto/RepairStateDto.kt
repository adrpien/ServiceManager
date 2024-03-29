package com.example.servicemanager.feature_app_data.remote.dto

import com.example.servicemanager.feature_app_data.local.room.entities.RepairStateEntity

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