package com.example.servicemanager.common_domain.use_cases.repair_states

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.RepairState
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class UpdateRepairState @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(repairState: RepairState): Resource<String> {
        return try {
            repository.updateRepairState(repairState)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )

        }
    }

}