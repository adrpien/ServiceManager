package com.example.feature_repairs_domain

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import com.example.test.repair
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class RepairRepositoryFake: RepairRepository {

    private val repairList: MutableList<Repair> = mutableListOf()
    private val repairListFlow = MutableStateFlow<Resource<List<Repair>>>(Resource(ResourceState.SUCCESS, repairList))

    private var shouldReturnError = false

    override fun getRepair(repairId: String): Flow<Resource<Repair>> {
        return repairListFlow
            .map {
                val repair: Repair = it.data?.find { it.repairId == repairId } ?: repair("0")
                Resource(ResourceState.SUCCESS, repair)
            }
    }

    override fun getRepairList(): Flow<Resource<List<Repair>>> {
        return repairListFlow
    }

    override fun insertRepair(repair: Repair): Flow<Resource<String>> {
        repairList.add(repair)
        repairListFlow.value = Resource(ResourceState.SUCCESS, repairList)
        return repairListFlow
            .map {
                val repair: Repair = it.data?.find { it.repairId == repair.repairId } ?: repair("0")
                Resource(ResourceState.SUCCESS, repair.repairId)
            }
    }

    override fun updateRepair(repair: Repair): Flow<Resource<String>> {
        val repairIndex = repairList.indexOf(repairList.find { it.repairId == repair.repairId })
        repairList.removeAt(repairIndex)
        repairList.add(repair)
        repairList.forEach { it.repairId == repair.repairId }
        repairListFlow.value = Resource(ResourceState.SUCCESS, repairList)
        return repairListFlow
            .map {
                val repair: Repair = it.data?.find { it.repairId == repair.repairId } ?: repair("0")
                Resource(ResourceState.SUCCESS, repair.repairId)
            }
    }

    override fun getRepairListFromLocal(): Flow<Resource<List<Repair>>> {
        return repairListFlow
    }

    fun setShouldReturnError(value: Boolean){
        shouldReturnError = value
    }
}