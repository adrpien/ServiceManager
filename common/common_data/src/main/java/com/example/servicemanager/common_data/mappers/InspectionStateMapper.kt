package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.InspectionStateEntity
import com.example.servicemanager.common_domain.model.InspectionState

fun InspectionState.toInspectionStateEntity(): InspectionStateEntity {
    return InspectionStateEntity(
        inspectionStateId = inspectionStateId,
        inspectionState = inspectionState
    )
}


