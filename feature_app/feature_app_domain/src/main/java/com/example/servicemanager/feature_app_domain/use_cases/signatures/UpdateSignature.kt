package com.example.servicemanager.feature_app.domain.use_cases.signatures


import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSignature @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(signatureId: String, byteArray: ByteArray): Flow<com.example.core.util.Resource<String>> {
        if(signatureId.isEmpty()){
            return flow {
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.ERROR,
                        null,
                        "Update signature unknown error"
                    )
                )
            }
        }
        return repository.updateSignature(signatureId, byteArray)
    }

}