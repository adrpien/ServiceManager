package com.example.servicemanager.feature_app_data.remote.dto

import com.example.servicemanager.feature_app_data.local.room.entities.TechnicianEntity

data class TechnicianDto(
    val technicianId: String,
    val name: String = ""
) {

    fun toTechnicianEntity(): TechnicianEntity {
        return TechnicianEntity(
            technicianId = technicianId,
            name = name
        )
    }

    fun toTechnicianDto(): TechnicianDto {
        return TechnicianDto(
            technicianId = technicianId,
            name = name
        )
    }
}
