package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.TechnicianEntity

data class Technician(
    val technicianId: String = "",
    val name: String = ""
) {
    fun toTechnicianEntity(): TechnicianEntity {
        return TechnicianEntity(
            technicianId = technicianId,
            name = name
        )
    }
}
