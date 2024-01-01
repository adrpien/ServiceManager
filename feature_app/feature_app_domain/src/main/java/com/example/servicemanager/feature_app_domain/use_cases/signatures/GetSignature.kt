package com.example.servicemanager.feature_app_domain.use_cases.signatures

import com.example.core.util.Resource
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSignature @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(signatureId: String): Flow<Resource<ByteArray>> {
        if(signatureId.isEmpty()){
            return flow {
                emit(
                    Resource(
                        com.example.core.util.ResourceState.ERROR,
                        null,
                        UiText.StringResource(R.string.get_signature_unknown_error)
                    )
                )
            }
        }
        return repository.getSignature(signatureId)
    }

}