package com.example.servicemanager.feature_app.domain.use_cases.signatures


import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSignature @Inject constructor (
    private val repository: AppRepository
    ) {

    operator fun invoke(signatureId: String, byteArray: ByteArray): Flow<Resource<String>> {
        return repository.createSignature(signatureId, byteArray)
    }

}