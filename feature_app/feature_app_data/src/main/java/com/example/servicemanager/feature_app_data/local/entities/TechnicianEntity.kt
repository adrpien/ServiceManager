package com.example.servicemanager.feature_app_data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app_domain.model.Technician

@Entity
data class TechnicianEntity(
    @PrimaryKey(autoGenerate = false)
    val technicianId: String,
    val name: String = ""
) {

    fun toTechnician(): Technician {
        return Technician(
            technicianId = technicianId,
            name = name
        )
    }
}
