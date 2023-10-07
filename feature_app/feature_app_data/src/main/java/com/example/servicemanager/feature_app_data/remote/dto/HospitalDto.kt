package com.example.servicemanager.feature_app_data.remote.dto

import com.example.servicemanager.feature_app_data.local.entities.HospitalEntity

data class HospitalDto(
    val hospitalId: String,
    val name: String = ""
){
    fun toHospitalEntity(): HospitalEntity {
        return HospitalEntity(
            hospitalId = hospitalId,
            hospital = name
        )
    }

    fun toHospitalDto(): HospitalDto {
        return HospitalDto(
            hospitalId = hospitalId,
            name = name
        )
    }
}