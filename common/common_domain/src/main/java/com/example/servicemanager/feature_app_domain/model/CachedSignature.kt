package com.example.servicemanager.feature_app_domain.model

import android.net.Uri
import com.example.servicemanager.feature_app_domain.util.CacheOperationType

data class CachedSignature(
    val signatureId: String = "",
    val operationType: CacheOperationType = CacheOperationType.Insert(),
    val uri: Uri = Uri.EMPTY
)