package com.example.servicemanager.feature_app.domain.model


data class EstState (
    val estStateId: String = "",
    val estState: String = ""
){
    fun toEstStateEntity(): EstStateEntity {
        return EstStateEntity(
            estStateId = estStateId,
            name = estState
        )
    }

}

