package com.example.servicemanager.feature_repairs_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repair: Repair): Flow<com.example.core.util.Resource<String>> {
        return if (repair.repairId != "0")  repository.updateRepair(repair)
        else {
            flow<com.example.core.util.Resource<String>> {
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.ERROR,
                        "Repair update unknown error",
                        "Repair update unknown error"
                    )
                )
            }
        }
    }
}