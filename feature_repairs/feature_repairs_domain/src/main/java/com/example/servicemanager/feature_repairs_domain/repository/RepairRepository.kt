package com.example.servicemanager.feature_repairs_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_repairs_domain.model.Repair
import kotlinx.coroutines.flow.Flow

interface RepairRepository {

    /* ***** Repairs **************************************************************************** */
    fun getRepair(repairId: String): Flow<Resource<Repair>>
    fun getRepairList(): Flow<Resource<List<Repair>>>
    suspend fun insertRepair (repair: Repair): Resource<String>
    suspend fun updateRepair (repair: Repair): Resource<String>
    suspend fun getRepairListFromLocal(): Resource<List<Repair>>
}