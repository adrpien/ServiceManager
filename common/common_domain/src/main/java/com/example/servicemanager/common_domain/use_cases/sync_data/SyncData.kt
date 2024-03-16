package com.example.servicemanager.common_domain.use_cases.sync_data


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class SyncData @Inject constructor (
    private val repository: AppRepository
    ) {

    suspend operator fun invoke(): Resource<String> {
        return try {
            var isSuccessful = true
            val listOfSignatures = repository.getCachedSignatureList().data
            if (listOfSignatures == null) isSuccessful = false
            listOfSignatures?.forEach { signature ->
                val result = repository.syncSignature(signature)
                if (result.resourceState == ResourceState.ERROR) {
                    isSuccessful = false
                }
                if (result.resourceState == ResourceState.SUCCESS) {
                    repository.deleteCachedSignature(signature.signatureId)
                }
            }
            if(isSuccessful) {
                Resource(
                    ResourceState.SUCCESS,
                    null,
                    UiText.StringResource(R.string.syncing_data_success)
                )
            } else {
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.syncing_data_error)
                )
            }
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}