package com.adrpien.tiemed.domain.use_case.repairs

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.Repair
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepair @Inject constructor (
    private val repository: TiemedRepository
) {

    operator fun invoke(repairId: String): Flow<Resource<Repair>> {
        if(repairId.isBlank()) {
            return flow {  }
        }
        return  repository.getRepair(repairId)
    }

}