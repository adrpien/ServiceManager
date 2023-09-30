package com.example.servicemanager.feature_app.domain.model


data class  InspectionState (
    val inspectionStateId: String = "",
    val inspectionState: String =  ""
        ){
    fun toInspectionStateEntity(): InspectionStateEntity {
        return InspectionStateEntity(
            inspectionStateId = inspectionStateId,
            inspectionState = inspectionState
        )
    }

}
