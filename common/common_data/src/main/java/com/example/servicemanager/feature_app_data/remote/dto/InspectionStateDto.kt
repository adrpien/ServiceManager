package com.example.servicemanager.feature_app_data.remote.dto

import com.example.servicemanager.feature_app_data.local.room.entities.InspectionStateEntity

data class  InspectionStateDto (
    val inspectionStateId: String,
    val state: String =  ""
){

    fun toInspectionStateEntity(): InspectionStateEntity {
        return InspectionStateEntity(
            inspectionStateId = inspectionStateId,
            inspectionState = state
        )
    }

    fun toInspectionStateDto(): InspectionStateDto {
        return InspectionStateDto(
            inspectionStateId = inspectionStateId,
            state = state
        )
    }
}
