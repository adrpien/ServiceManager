package com.example.servicemanager.feature_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app.domain.model.InspectionState

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
