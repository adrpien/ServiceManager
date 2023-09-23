package com.example.servicemanager.feature_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app.domain.model.EstState

@Entity
data class EstStateEntity (
    @PrimaryKey(autoGenerate = false)
    val estStateId: String,
    val name: String = ""
){

    fun toEstState(): EstState {
        return EstState(
            estStateId = estStateId,
            estState = name
        )
    }
}

