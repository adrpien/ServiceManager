package com.example.servicemanager.feature_app_domain.use_cases.est_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateEstState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(estState: EstState): Resource<String> {
        return try {
            repository.updateEstState(estState)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}