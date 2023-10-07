package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.entities.InspectionStateEntity
import com.example.servicemanager.feature_app_domain.model.InspectionState

fun InspectionState.toInspectionStateEntity(): InspectionStateEntity {
    return InspectionStateEntity(
        inspectionStateId = inspectionStateId,
        inspectionState = inspectionState
    )
}


