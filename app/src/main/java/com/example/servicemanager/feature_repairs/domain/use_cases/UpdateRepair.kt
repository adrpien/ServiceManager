package com.adrpien.tiemed.domain.use_case.repairs

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.feature_repairs.domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repair: Repair): Flow<Resource<String>> {
        return if (repair.repairId != "0")  repository.updateRepair(repair)
        else {
            flow<Resource<String>> {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Repair update unknown error",
                        "Repair update unknown error"
                    )
                )
            }
        }
    }
}