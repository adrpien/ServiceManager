package com.example.servicemanager.common_data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.common_domain.model.EstState

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

