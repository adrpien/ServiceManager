package com.example.servicemanager.common_data.mappers

import com.example.servicemanager.common_data.local.room.entities.CachedSignatureEntity
import com.example.servicemanager.common_domain.model.CachedSignature

fun CachedSignature.toCachedSignatureEntity(): CachedSignatureEntity {
    return CachedSignatureEntity(
        signatureId = signatureId,
        operationType = operationType.operationName,
        uri = uri.path ?: ""
    )
}

