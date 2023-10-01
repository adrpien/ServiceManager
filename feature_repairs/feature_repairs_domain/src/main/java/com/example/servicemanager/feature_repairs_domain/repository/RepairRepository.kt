package com.example.servicemanager.feature_repairs_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_repairs_domain.model.Repair
import kotlinx.coroutines.flow.Flow

interface RepairRepository {


    /* ***** Repairs **************************************************************************** */
    fun getRepair(repairId: String): Flow<com.example.core.util.Resource<Repair>>
    fun getRepairList(): Flow<com.example.core.util.Resource<List<Repair>>>
    fun insertRepair (repair: Repair): Flow<com.example.core.util.Resource<String>>
    fun updateRepair (repair: Repair): Flow<com.example.core.util.Resource<String>>
    fun getRepairListFromLocal(): Flow<com.example.core.util.Resource<List<Repair>>>


}