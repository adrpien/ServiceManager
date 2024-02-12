package com.example.servicemanager.feature_app_data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app_domain.model.InspectionState

@Entity
data class  InspectionStateEntity (
    @PrimaryKey(autoGenerate = false)
    val inspectionStateId: String,
    val inspectionState: String =  ""
    ){

    fun toInspectionState(): InspectionState {
        return InspectionState(
            inspectionStateId = inspectionStateId,
            inspectionState = inspectionState
        )
    }
}
