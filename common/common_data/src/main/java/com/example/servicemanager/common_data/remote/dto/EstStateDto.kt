package com.example.servicemanager.common_data.remote.dto

import com.example.servicemanager.common_data.local.room.entities.EstStateEntity

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

