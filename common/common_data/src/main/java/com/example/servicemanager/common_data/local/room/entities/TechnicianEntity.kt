package com.example.servicemanager.common_data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.common_domain.model.Technician

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
