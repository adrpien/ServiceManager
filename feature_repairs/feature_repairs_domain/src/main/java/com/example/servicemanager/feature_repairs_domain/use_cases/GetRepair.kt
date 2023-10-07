package com.example.servicemanager.feature_repairs_domain.use_cases


import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repairId: String): Flow<com.example.core.util.Resource<Repair>> {
        if(repairId.isEmpty()) {
            return flow {
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.ERROR,
                        Repair(),
                        "Get inspection unknown error"
                    )
                )
            }
        }
        return repository.getRepair(repairId)
    }

}