package com.adrpien.tiemed.domain.use_case.room

import com.example.servicemanager.core.util.Resource
import com.adrpien.tiemed.domain.model.RepairState
import com.adrpien.tiemed.domain.repository.TiemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRoomRepairStateList @Inject constructor(
    private val tiemedRepository: TiemedRepository
) {

    operator fun invoke(repairStateList: List<RepairState>): Flow<Resource<Boolean>> {
        return tiemedRepository.updateRoomRepairStateList(repairStateList)
    }
}