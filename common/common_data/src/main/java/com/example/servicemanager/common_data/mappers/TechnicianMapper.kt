package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.TechnicianEntity
import com.example.servicemanager.common_domain.model.Technician

fun Technician.toTechnicianEntity(): TechnicianEntity {
    return TechnicianEntity(
        technicianId = technicianId,
        name = name
    )
}