package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.HospitalEntity
import com.example.servicemanager.common_data.remote.dto.HospitalDto
import com.example.servicemanager.common_domain.model.Hospital

fun Hospital.toHospitalEntity(): HospitalEntity {
    return HospitalEntity(
        hospitalId = hospitalId,
        hospital = hospital
    )
}
fun Hospital.toHospitalDto(): HospitalDto {
    return HospitalDto(
        hospitalId = hospitalId,
        name = hospital
    )
}
