package com.example.servicemanager.feature_app_domain.use_cases.inspection_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import javax.inject.Inject

class CreateInspectionState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(inspectionState: InspectionState): Resource<String> {
        return try {
            repository.createInspectionState(inspectionState)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.inspection_state_name_can_not_be_empty)
            )
        }
    }

}