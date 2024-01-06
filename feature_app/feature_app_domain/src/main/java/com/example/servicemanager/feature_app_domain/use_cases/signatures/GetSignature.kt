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
        return try {
            repository.getSignature(signatureId)
        } catch (e: IllegalArgumentException) {
            flow {
                emit(
                    Resource(
                        com.example.core.util.ResourceState.ERROR,
                        null,
                        UiText.StringResource(R.string.unknown_error)
                    )
                )
            }

        }
    }
}