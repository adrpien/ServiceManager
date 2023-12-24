package com.example.servicemanager.feature_app_domain.use_cases.repair_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateRepairState @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(repairState: RepairState): Flow<Resource<String>> {
        return if(repairState.repairStateId != "") {
            repository.createRepairState(repairState)
        } else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Repair state name can not be empty",
                        UiText.StringResource(R.string.repair_state_name_can_not_be_empty)
                    )
                )
            }
        }
    }

}