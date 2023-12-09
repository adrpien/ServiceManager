package com.example.servicemanager.feature_app_domain.use_cases.signatures


import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.repository.AppRepository
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
                        UiText.StringResource(R.string.update_signature_unknown_error)
                    )
                )
            }
        }
        return repository.updateSignature(signatureId, byteArray)
    }

}