package com.example.servicemanager.feature_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app.domain.model.Hospital

@Entity
data class HospitalEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val hospitalId: String,
    val name: String = ""
){
    fun toHospital(): Hospital {
        return Hospital(
            hospitalId = hospitalId,
            hospitalName = name
        )
    }
}