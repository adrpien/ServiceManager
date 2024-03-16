package com.example.servicemanager.common_data.remote.dto

import com.example.servicemanager.common_data.local.room.entities.HospitalEntity

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