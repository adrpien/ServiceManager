package com.example.servicemanager.feature_app_data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app_domain.model.CachedSignature
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.util.CacheOperationType

@Entity
data class CachedSignatureEntity(
    @PrimaryKey(autoGenerate = false)
    val signatureId: String,
    val operationType: String = "",
    val uri: String = ""
){
    fun toCachedSignature(): CachedSignature {
        return CachedSignature(
            signatureId = signatureId,
            operationType = CacheOperationType.getCacheOperationType(operationType)
        )
    }
}

