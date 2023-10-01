package com.example.servicemanager.feature_app.domain.model

import com.example.servicemanager.feature_app.data.local.entities.HospitalEntity
import com.example.servicemanager.feature_app.data.remote.dto.HospitalDto

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
