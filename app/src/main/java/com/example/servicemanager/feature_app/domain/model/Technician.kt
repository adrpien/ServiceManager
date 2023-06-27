package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.TechnicianEntity

data class Technician(
    var technicianId: String = "",
    var technicianName: String = ""
) {
    fun toTechnicianEntity(): TechnicianEntity {
        return TechnicianEntity(
            technicianId = technicianId,
            technicianName = technicianName
        )
    }
}
