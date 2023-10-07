package com.example.servicemanager.feature_app_data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app_domain.model.Hospital

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