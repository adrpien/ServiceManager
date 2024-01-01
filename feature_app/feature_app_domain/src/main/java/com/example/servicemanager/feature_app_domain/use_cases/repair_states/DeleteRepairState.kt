package com.example.servicemanager.feature_app_domain.use_cases.repair_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteRepairState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(repairState: RepairState): Resource<String> {
        return if(repairState.repairStateId != "") {
            repository.deleteRepairState(repairState.repairStateId)
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repair_state_delete_unknown_error)
            )
        }
    }

}