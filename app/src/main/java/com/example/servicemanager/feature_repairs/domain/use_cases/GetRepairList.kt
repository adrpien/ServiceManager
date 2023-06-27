package com.adrpien.tiemed.domain.use_case.repairs


import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.feature_repairs.domain.repository.RepairRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepairList @Inject constructor (
    private val repository: RepairRepository
) {

    operator fun invoke(): Flow<Resource<List<Repair>>> {
        return repository.getRepairList()
    }

}