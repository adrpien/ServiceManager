package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.InspectionStateEntity

fun InspectionState.toInspectionStateEntity(): InspectionStateEntity {
    return InspectionStateEntity(
        inspectionStateId = inspectionStateId,
        inspectionState = inspectionState
    )
}


