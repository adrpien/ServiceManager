package com.example.servicemanager.feature_app_domain.use_cases.signatures


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSignature @Inject constructor (
    private val repository: AppRepository
    ) {

    suspend operator fun invoke(signatureId: String, byteArray: ByteArray): Resource<String> {
        if(signatureId.isEmpty()){
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.update_signature_unknown_error)
            )
        }
        return repository.createSignature(signatureId, byteArray)
    }

}