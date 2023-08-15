package com.adrpien.tiemed.domain.use_case.repairs


import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.feature_repairs.domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repairId: String): Flow<Resource<Repair>> {
        if(repairId.isEmpty()) {
            return flow {
                emit(Resource(ResourceState.ERROR, Repair(), "Get inspection unknown error"))
            }
        }
        return repository.getRepair(repairId)
    }

}