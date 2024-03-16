package com.example.servicemanager.common_domain.use_cases.inspection_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.InspectionState
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class UpdateInspectionState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(inspectionState: InspectionState): Resource<String> {
        return try {
            repository.updateInspectionState(inspectionState)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}