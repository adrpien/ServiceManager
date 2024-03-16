package com.example.servicemanager.common_domain.use_cases.est_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.EstState
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class DeleteEstState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(estState: EstState): Resource<String> {
        return try {
            repository.deleteEstState(estState.estStateId)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )

        }
    }

}