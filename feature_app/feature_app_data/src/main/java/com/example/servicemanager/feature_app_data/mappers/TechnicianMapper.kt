package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.entities.TechnicianEntity
import com.example.servicemanager.feature_app_domain.model.Technician

fun Technician.toTechnicianEntity(): TechnicianEntity {
    return TechnicianEntity(
        technicianId = technicianId,
        name = name
    )
}