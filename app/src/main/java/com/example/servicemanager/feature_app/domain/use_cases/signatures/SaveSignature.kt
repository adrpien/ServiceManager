package com.example.servicemanager.feature_app.domain.use_cases.signatures


import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveSignature @Inject constructor (
    private val repository: AppRepository
    ) {

    operator fun invoke(signatureId: String, byteArray: ByteArray): Flow<Resource<String>> {
        if(signatureId.isEmpty()){
            return flow {
                emit(Resource(ResourceState.ERROR, null, "Save signature unknown error"))
            }
        }
        return repository.createSignature(signatureId, byteArray)
    }

}