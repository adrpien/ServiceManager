package com.example.servicemanager.feature_repairs_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.logger_domain.logger.AppLogger
import com.example.logger_domain.util.EventLogType
import com.example.servicemanager.feature_repairs_data.local.RepairDatabaseDao
import com.example.servicemanager.feature_repairs_data.mappers.toRepairEntity
import com.example.servicemanager.feature_repairs_data.remote.RepairFirebaseApi
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.*


class  RepairRepositoryImplementation(
    val repairDatabaseDao: RepairDatabaseDao,
    val repairFirebaseApi: RepairFirebaseApi,
    val appLogger: AppLogger
): RepairRepository {



    /* ********************************* REPAIRS ******************************************** */
    override fun getRepairList() = flow {
        var repairList: List<Repair>
        repairList = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(
            Resource(
                ResourceState.LOADING,
                repairList,
                "Locally cached list"
            )
        )
        val list = repairFirebaseApi.getRepairList()
        if(list.isNotEmpty()) {
            repairDatabaseDao.deleteAllRepairs()
            for (device in list) {
                repairDatabaseDao.insertRepair(device.toRepairEntity())
            }
            repairList = repairDatabaseDao.getRepairList().map { it.toRepair() }
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    repairList,
                    "Device list fetching finished"
                )
            )
        }
    }


    override fun getRepair(repairId: String): Flow<Resource<Repair>> = flow{
        var repair: Repair
        repair = repairDatabaseDao.getRepair(repairId).toRepair()
        emit(
            Resource(
                ResourceState.LOADING,
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
                Resource(
                    ResourceState.SUCCESS,
                    repair,
                    "Repair record fetching finished"
                )
            )
        }
    }

    override fun insertRepair(repair: Repair): Flow<Resource<String>> {
        appLogger.logRepair(
            eventLogType = EventLogType.NewRecordLog(),
            repair = repair
        )
        return repairFirebaseApi.createRepair(repair)
    }

    override fun updateRepair(repair: Repair): Flow<Resource<String>> {
        appLogger.logRepair(
            eventLogType = EventLogType.RecordUpdateLog(),
            repair = repair
        )
        return repairFirebaseApi.updateRepair(repair)
    }

    override fun getRepairListFromLocal(): Flow<Resource<List<Repair>>> = flow {

        var repairList: List<Repair> = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(
            Resource(
                ResourceState.SUCCESS,
                repairList,
                "Locally storaged list"
            )
        )
    }

}
