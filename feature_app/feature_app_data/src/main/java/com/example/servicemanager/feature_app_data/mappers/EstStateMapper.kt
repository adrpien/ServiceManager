package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.entities.EstStateEntity
import com.example.servicemanager.feature_app_domain.model.EstState


fun EstState.toEstStateEntity(): EstStateEntity {
    return EstStateEntity(
        estStateId = estStateId,
        name = estState
    )
}


