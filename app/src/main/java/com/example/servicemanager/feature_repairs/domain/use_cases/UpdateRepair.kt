package com.adrpien.tiemed.domain.use_case.repairs

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.Repair
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRepair @Inject constructor (
    private val repository: TiemedRepository
) {

    operator fun invoke(repair: Repair): Flow<Resource<Boolean>> {
        return repository.updateRepair(repair)
    }

}