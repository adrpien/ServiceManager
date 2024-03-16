package com.example.servicemanager.common_domain.use_cases.signatures


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class UpdateSignature @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(signatureId: String, byteArray: ByteArray): Resource<String> {
        return try {
            repository.updateSignature(signatureId, byteArray)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}