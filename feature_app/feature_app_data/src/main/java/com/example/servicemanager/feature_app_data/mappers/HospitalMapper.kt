package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.entities.HospitalEntity
import com.example.servicemanager.feature_app_data.remote.dto.HospitalDto
import com.example.servicemanager.feature_app_domain.model.Hospital

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
