package com.example.servicemanager.feature_app_domain.use_cases.signatures


import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSignature @Inject constructor (
    private val repository: AppRepository
    ) {

    operator fun invoke(signatureId: String, byteArray: ByteArray): Flow<Resource<String>> {
        if(signatureId.isEmpty()){
            throw IllegalArgumentException()
        }
        return repository.createSignature(signatureId, byteArray)
    }

}