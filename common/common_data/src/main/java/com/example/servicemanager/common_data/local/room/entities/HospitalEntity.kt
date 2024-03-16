package com.example.servicemanager.common_data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.common_domain.model.Hospital

@Entity
data class HospitalEntity(
    @PrimaryKey(autoGenerate = false)
    val hospitalId: String,
    val hospital: String = ""
){
    fun toHospital(): Hospital {
        return Hospital(
            hospitalId = hospitalId,
            hospital = hospital
        )
    }
}