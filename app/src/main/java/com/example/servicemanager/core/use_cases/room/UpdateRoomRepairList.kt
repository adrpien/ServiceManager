package com.adrpien.tiemed.domain.use_case.room

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.Repair
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRoomRepairList @Inject constructor(
    private val tiemedRepository: TiemedRepository
) {

    operator fun invoke(repairList: List<Repair>): Flow<Resource<Boolean>> {
        return tiemedRepository.updateRoomRepairList(repairList)
    }
}