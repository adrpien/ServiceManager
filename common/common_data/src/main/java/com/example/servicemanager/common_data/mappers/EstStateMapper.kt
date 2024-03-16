package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.EstStateEntity
import com.example.servicemanager.common_domain.model.EstState


fun EstState.toEstStateEntity(): EstStateEntity {
    return EstStateEntity(
        estStateId = estStateId,
        name = estState
    )
}


