package com.example.servicemanager.feature_app.domain.model


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