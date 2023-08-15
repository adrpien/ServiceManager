package com.example.servicemanager.feature_repairs.domain.repository

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_repairs.domain.model.Repair
import kotlinx.coroutines.flow.Flow

interface RepairRepository {


    /* ***** Repairs **************************************************************************** */
    fun getRepair(repairId: String): Flow<Resource<Repair>>
    fun getRepairList(): Flow<Resource<List<Repair>>>
    fun insertRepair (repair: Repair): Flow<Resource<String>>
    fun updateRepair (repair: Repair): Flow<Resource<String>>
    fun getRepairListFromLocal(): Flow<Resource<List<Repair>>>


}