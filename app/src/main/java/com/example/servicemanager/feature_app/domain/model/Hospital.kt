package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.HospitalEntity
import com.example.servicemanager.feature_app.data.remote.dto.HospitalDto

data class Hospital(
    val hospitalId: String = "",
    val hospitalName: String = ""
){
    fun toHospitalEntity(): HospitalEntity {
        return HospitalEntity(
            hospitalId = hospitalId,
            name = hospitalName
        )
    }

    fun toHospitalDto(): HospitalDto {
        return HospitalDto(
            hospitalId = hospitalId,
            name = hospitalName
        )
    }
}