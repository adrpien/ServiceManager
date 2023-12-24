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

    operator fun invoke(estState: EstState): Flow<Resource<String>> {
        return if(estState.estStateId != "") {
            repository.updateEstState(estState)
        } else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "EstState update unknown error",
                        UiText.StringResource(R.string.eststate_update_unknown_error)
                    )
                )
            }
        }
    }

}