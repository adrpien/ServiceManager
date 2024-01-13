package com.example.servicemanager.feature_repairs_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_repairs_data.R
import com.example.logger.AppLogger
import com.example.servicemanager.feature_repairs_data.local.RepairDatabaseDao
import com.example.servicemanager.feature_repairs_data.mappers.toRepairEntity
import com.example.servicemanager.feature_repairs_data.remote.RepairFirebaseApi
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import kotlinx.coroutines.flow.*


class  RepairRepositoryImplementation(
    val repairDatabaseDao: RepairDatabaseDao,
    val repairFirebaseApi: RepairFirebaseApi,
    val appLogger: AppLogger<Any>
): RepairRepository {



    /* ********************************* REPAIRS ******************************************** */
    override fun getRepairList() = flow {
        var repairList: List<Repair>
        repairList = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(
            Resource(
                ResourceState.LOADING,
                repairList,
                UiText.StringResource(R.string.locally_cached_list)
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
                    UiText.StringResource(R.string.repair_list_fetching_finished)
                )
            )
        }
    }


    override fun getRepair(repairId: String): Flow<Resource<Repair>> = flow{
        if (repairId == ""){
            throw IllegalArgumentException("repairId can not be empty")
        }
        var repair: Repair? = null
        repair = repairDatabaseDao.getRepair(repairId).toRepair()
        emit(
            Resource(
                ResourceState.LOADING,
                repair,
                UiText.StringResource(R.string.locally_cached_record)
            )
        )
        repair = repairFirebaseApi.getRepair(repairId)
        if(repair != null) {
            repairDatabaseDao.deleteRepair(repairId)
            repairDatabaseDao.insertRepair(repair.toRepairEntity())
            repair = repairDatabaseDao.getRepair(repairId).toRepair()
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    repair,
                    UiText.StringResource(R.string.repair_record_fetching_finished)
                )
            )
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.repair_record_fetching_error)
                )
            )
        }
    }

    override suspend fun insertRepair(repair: Repair): Resource<String> {
        if (repair.repairId == ""){
            throw IllegalArgumentException("repairId can not be empty")
        }
        appLogger.logEvent(
            eventLogType = com.example.logger.EventLogType.NewRecordLog(),
            dataClassObject  = repair
        )
        return repairFirebaseApi.createRepair(repair)
    }

    override suspend fun updateRepair(repair: Repair): Resource<String> {
        if (repair.repairId == ""){
            throw IllegalArgumentException("repairId can not be empty")
        }
        appLogger.logEvent(
            eventLogType = com.example.logger.EventLogType.RecordUpdateLog(),
            dataClassObject = repair
        )
        return repairFirebaseApi.updateRepair(repair)
    }

    override fun getRepairListFromLocal(): Flow<Resource<List<Repair>>> = flow {

        var repairList: List<Repair> = repairDatabaseDao.getRepairList().map { it.toRepair() }
        emit(
            Resource(
                ResourceState.SUCCESS,
                repairList,
                UiText.StringResource(R.string.locally_cached_list)
            )
        )
    }

}
