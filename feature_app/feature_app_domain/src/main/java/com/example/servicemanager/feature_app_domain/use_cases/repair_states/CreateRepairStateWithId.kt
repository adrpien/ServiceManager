package com.example.servicemanager.feature_app_domain.use_cases.repair_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import javax.inject.Inject

class CreateRepairStateWithId @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(repairState: RepairState): Resource<String> {
        return try {
            repository.createRepairStateWithId(repairState)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}