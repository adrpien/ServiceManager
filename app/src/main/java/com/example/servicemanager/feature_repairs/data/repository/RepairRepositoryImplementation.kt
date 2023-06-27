package com.example.servicemanager.feature_repairs.data.repository


import android.util.Log
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_repairs.data.local.RepairDatabaseDao
import com.example.servicemanager.feature_repairs.data.remote.RepairFirebaseApi
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.feature_repairs.domain.repository.RepairRepository
import kotlinx.coroutines.flow.*


class  RepairRepositoryImplementation(
    val repairDatabaseDao: RepairDatabaseDao,
    val repairFirebaseApi: RepairFirebaseApi
): RepairRepository {

    private val REPAIR_REPOSITORY_IMPLEMENTATION = "TIEMED REPOSITORY IMPLEMENTATION"


    /* ********************************* REPAIRS ******************************************** */
    override fun getRepairList() = flow {
        Log.d(REPAIR_REPOSITORY_IMPLEMENTATION, "Repair list fetching started")
        emit(Resource(ResourceState.LOADING, null, "Repair list fetching started"))
        var repairList: List<Repair> = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(Resource(ResourceState.LOADING, repairList, "Locally cached list"))
        val list = repairFirebaseApi.getRepairList()?: emptyList()
        if(list.isNotEmpty()) {
            repairDatabaseDao.deleteAllRepairs()
            for (device in list) {
                repairDatabaseDao.insertRepair(device.toRepairEntity())
            }
            repairList = repairDatabaseDao.getRepairList().map { it.toRepair() }
            emit(Resource(ResourceState.SUCCESS, repairList, "Device list fetching finished"))
        }
    }


    override fun getRepair(repairId: String): Flow<Resource<Repair>> = flow{
        Log.d(REPAIR_REPOSITORY_IMPLEMENTATION, "Repair record fetching started")
        emit(Resource(ResourceState.LOADING, null, "Repair record fetching started"))
        var repair = repairDatabaseDao.getRepair(repairId).toRepair()
        emit(Resource(ResourceState.LOADING, repair, "Locally cached record"))
        val record = repairFirebaseApi.getRepair(repairId)
        if(record != null) {
            repairDatabaseDao.deleteRepair(repairId)
            repairDatabaseDao.insertRepair(record.toRepairEntity())
            repair = repairDatabaseDao.getRepair(repairId).toRepair()
            emit(Resource(ResourceState.SUCCESS, repair, "Repair record fetching finished"))
        }
    }

    override fun insertRepair(repair: Repair): Flow<Resource<Boolean>> {
        return repairFirebaseApi.createRepair(repair)
    }

    override fun updateRepair(repair: Repair): Flow<Resource<Boolean>> {
        return repairFirebaseApi.updateRepair(repair)
    }

    override fun getRepairListFromLocal(): Flow<Resource<List<Repair>>> = flow {
        Log.d(REPAIR_REPOSITORY_IMPLEMENTATION, "Repair list fetching from local started")
        emit(Resource(ResourceState.LOADING, null, "Repair list fetching form local started"))
        var repairList: List<Repair> = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(Resource(ResourceState.SUCCESS, repairList, "Locally storaged list"))
    }

}
