package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.EstStateEntity


fun EstState.toEstStateEntity(): EstStateEntity {
    return EstStateEntity(
        estStateId = estStateId,
        name = estState
    )
}


