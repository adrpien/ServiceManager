package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.RepairStateEntity

data class RepairState (
    val repairStateId: String = "",
    val repairState: String = ""
        ) {

    fun toRepairStateEntity(): RepairStateEntity {
        return  RepairStateEntity(
            repairStateId = repairStateId,
            repairState = repairState
        )
    }

}