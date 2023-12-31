package com.example.servicemanager.feature_repairs_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_domain.R
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRepair @Inject constructor (
    private val repository: RepairRepository
) {

    // TODO opening date should not be bigger tha repairing date and returning date should be bigger than repairing date
    suspend operator fun invoke(repair: Repair): Resource<String> {
        if (repair.repairId != "0") {
            return repository.updateRepair(repair)
        } else {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.repair_update_unknown_error)
            )

        }
    }
}