package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.EstStateEntity

data class EstState (
    var estStateId: String = "",
    var estState: String = ""
){
    fun toEstStateEntity(): EstStateEntity {
        return EstStateEntity(
            estStateId = estStateId,
            estState = estState
        )
    }

}

