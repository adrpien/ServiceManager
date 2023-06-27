package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.InspectionStateEntity

data class  InspectionState (
    var inspectionStateId: String = "",
    var inspectionState: String =  ""
        ){
    fun toInspectionStateEntity(): InspectionStateEntity {
        return InspectionStateEntity(
            inspectionStateId = inspectionStateId,
            inspectionState = inspectionState
        )
    }

}
