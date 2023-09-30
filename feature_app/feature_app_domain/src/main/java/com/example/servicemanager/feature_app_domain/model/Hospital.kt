package com.example.servicemanager.feature_app.domain.model



data class Hospital(
    val hospitalId: String = "",
    val hospital: String = ""
){
    fun toHospitalEntity(): HospitalEntity {
        return HospitalEntity(
            hospitalId = hospitalId,
            hospital = hospital
        )
    }

    fun toHospitalDto(): HospitalDto {
        return HospitalDto(
            hospitalId = hospitalId,
            name = hospital
        )
    }
}