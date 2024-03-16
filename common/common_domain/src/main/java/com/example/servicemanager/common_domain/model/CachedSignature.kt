package com.example.servicemanager.common_domain.model

import android.net.Uri
import com.example.servicemanager.common_domain.util.CacheOperationType

data class CachedSignature(
    val signatureId: String = "",
    val operationType: CacheOperationType = CacheOperationType.Insert(),
    val uri: Uri = Uri.EMPTY
)