package com.example.servicemanager.feature_app.data.remote.dto

import com.example.servicemanager.feature_app.data.local.entities.EstStateEntity

data class EstStateDto (
    val estStateId: String,
    val state: String = ""
){
        fun toEstStateEntity(): EstStateEntity {
            return EstStateEntity(
                estStateId = estStateId,
                name = state
            )
        }

    fun toEstStateDto(): EstStateDto {
        return EstStateDto(
            estStateId = estStateId,
            state = state
        )
    }
}

