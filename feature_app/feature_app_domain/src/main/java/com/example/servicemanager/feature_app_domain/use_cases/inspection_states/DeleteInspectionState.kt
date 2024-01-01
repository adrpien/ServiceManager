package com.example.servicemanager.feature_app_domain.use_cases.inspection_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteInspectionState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(inspectionState: InspectionState): Resource<String> {
        return if(inspectionState.inspectionStateId != "") {
            repository.deleteInspectionState(inspectionState.inspectionStateId)
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.inspection_state_delete_unknown_error)
            )
        }
    }

}