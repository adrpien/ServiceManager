package com.example.servicemanager.feature_repairs_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_repairs_data.local.RepairDatabaseDao
import com.example.servicemanager.feature_repairs_data.mappers.toRepairEntity
import com.example.servicemanager.feature_repairs_data.remote.RepairFirebaseApi
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.*


class  RepairRepositoryImplementation(
    val repairDatabaseDao: RepairDatabaseDao,
    val repairFirebaseApi: RepairFirebaseApi
): RepairRepository {

    private val REPAIR_REPOSITORY_IMPLEMENTATION = "TIEMED REPOSITORY IMPLEMENTATION"


    /* ********************************* REPAIRS ******************************************** */
    override fun getRepairList() = flow {
        var repairList: List<Repair> = listOf<Repair>()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                null,
                "Repair list fetching started"
            )
        )
        repairList = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                repairList,
                "Locally cached list"
            )
        )
        val list = repairFirebaseApi.getRepairList()?: emptyList()
        if(list.isNotEmpty()) {
            repairDatabaseDao.deleteAllRepairs()
            for (device in list) {
                repairDatabaseDao.insertRepair(device.toRepairEntity())
            }
            repairList = repairDatabaseDao.getRepairList().map { it.toRepair() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    repairList,
                    "Device list fetching finished"
                )
            )
        }
    }


    override fun getRepair(repairId: String): Flow<com.example.core.util.Resource<Repair>> = flow{
        var repair = Repair()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                null,
                "Repair record fetching started"
            )
        )
        repair = repairDatabaseDao.getRepair(repairId).toRepair()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                repair,
                "Locally cached record"
            )
        )
        val record = repairFirebaseApi.getRepair(repairId)
        if(record != null) {
            repairDatabaseDao.deleteRepair(repairId)
            repairDatabaseDao.insertRepair(record.toRepairEntity())
            repair = repairDatabaseDao.getRepair(repairId).toRepair()
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    repair,
                    "Repair record fetching finished"
                )
            )
        }
    }

    override fun insertRepair(repair: Repair): Flow<com.example.core.util.Resource<String>> {
        return repairFirebaseApi.createRepair(repair)
    }

    override fun updateRepair(repair: Repair): Flow<com.example.core.util.Resource<String>> {
        return repairFirebaseApi.updateRepair(repair)
    }

    override fun getRepairListFromLocal(): Flow<com.example.core.util.Resource<List<Repair>>> = flow {
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                null,
                "Repair list fetching form local started"
            )
        )
        var repairList: List<Repair> = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.SUCCESS,
                repairList,
                "Locally storaged list"
            )
        )
    }

}
