package com.example.servicemanager.feature_repairs_domain.use_cases


import com.example.core.util.Resource
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepair @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(repairId: String): Flow<Resource<Repair>> {
        if(repairId.isEmpty()) {
            throw IllegalArgumentException()
        }
        return repository.getRepair(repairId)
    }

}