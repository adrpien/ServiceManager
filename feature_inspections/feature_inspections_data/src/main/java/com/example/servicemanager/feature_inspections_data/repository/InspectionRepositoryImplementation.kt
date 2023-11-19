package com.example.servicemanager.feature_inspections_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.logger_domain.logger.AppLogger
import com.example.logger_domain.util.EventLogType
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabaseDao
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_data.mappers.toInspectionEntity
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.*


class  InspectionRepositoryImplementation(
    val inspectionDatabaseDao: InspectionDatabaseDao,
    val inspectionFirebaseApi: InspectionFirebaseApi,
    val appLogger: AppLogger
): InspectionRepository {

    /* ********************************* INSPECTIONS ******************************************** */
    override fun getInspectionList() = flow {
        var inspectionList: List<Inspection>
        inspectionList = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        emit(
            Resource(
                ResourceState.LOADING,
                inspectionList,
                "Locally cached list"
            )
        )
        val list = inspectionFirebaseApi.getInspectionList()
        if(list.isNotEmpty()) {
            inspectionDatabaseDao.deleteAllInspections()
            for (device in list) {
                inspectionDatabaseDao.insertInspection(device.toInspectionEntity())
            }
            inspectionList = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    inspectionList,
                    "Device list fetching finished"
                )
            )
        }
    }

    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> = flow{
        var inspection: Inspection
        inspection = inspectionDatabaseDao.getInspection(inspectionId).toInspection()
        emit(
            Resource(
                ResourceState.LOADING,
                inspection,
                "Locally cached record"
            )
        )
        val record = inspectionFirebaseApi.getInspection(inspectionId)
        if(record != null) {
            inspectionDatabaseDao.deleteInspection(inspectionId)
            inspectionDatabaseDao.insertInspection(record.toInspectionEntity())
            inspection = inspectionDatabaseDao.getInspection(inspectionId).toInspection()
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    inspection,
                    "Inspection record fetching finished"
                )
            )
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    inspection,
                    "Inspection record fetching error"
                )
            )
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> {
        appLogger.logInspection(
            eventLogType = EventLogType.NewRecordLog(),
            inspection = inspection,
        )
        return inspectionFirebaseApi.createInspection(inspection)
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<String>> {
        appLogger.logInspection(
            eventLogType = EventLogType.RecordUpdateLog(),
            inspection = inspection,
            )
        return inspectionFirebaseApi.updateInspection(inspection)
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>> = flow {
        var inspectionList: List<Inspection> = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        emit(
            Resource(
                ResourceState.SUCCESS,
                inspectionList,
                "Locally storaged list"
            )
        )
    }

}
