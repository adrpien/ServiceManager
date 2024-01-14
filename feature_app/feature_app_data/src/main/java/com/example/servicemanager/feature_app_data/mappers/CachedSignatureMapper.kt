package com.example.servicemanager.feature_app_data.mappers

import com.example.servicemanager.feature_app_data.local.room.entities.CachedSignatureEntity
import com.example.servicemanager.feature_app_data.local.room.entities.HospitalEntity
import com.example.servicemanager.feature_app_data.remote.dto.HospitalDto
import com.example.servicemanager.feature_app_domain.model.CachedSignature
import com.example.servicemanager.feature_app_domain.model.Hospital

fun CachedSignature.toCachedSignatureEntity(): CachedSignatureEntity {
    return CachedSignatureEntity(
        signatureId = signatureId,
        operationType = operationType.operationName,
        uri = uri.path ?: ""
    )
}

