package com.example.servicemanager.feature_inspections_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_data.R
import com.example.logger.AppLogger
import com.example.logger.EventLogType
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabaseDao
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_data.mappers.toInspectionEntity
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.*


class  InspectionRepositoryImplementation(
    val inspectionDatabaseDao: InspectionDatabaseDao,
    val inspectionFirebaseApi: InspectionFirebaseApi,
    val appLogger: com.example.logger.AppLogger
): InspectionRepository {

    /* ********************************* INSPECTIONS ******************************************** */
    override fun getInspectionList() = flow {
        var inspectionList: List<Inspection>
        inspectionList = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        emit(
            Resource(
                ResourceState.LOADING,
                inspectionList,
                UiText.StringResource(R.string.locally_cached_list)
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
                    UiText.StringResource(R.string.inspection_list_fetching_finished)
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
                UiText.StringResource(R.string.locally_cached_record)
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
                    UiText.StringResource(R.string.inspection_record_fetching_finished)
                )
            )
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    inspection,
                    UiText.StringResource(R.string.inspection_record_fetching_error)
                )
            )
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> {
        appLogger.logInspection(
            eventLogType = com.example.logger.EventLogType.NewRecordLog(),
            inspection = inspection,
        )
        return inspectionFirebaseApi.createInspection(inspection)
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<String>> {
        appLogger.logInspection(
            eventLogType = com.example.logger.EventLogType.RecordUpdateLog(),
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
                UiText.StringResource(R.string.locally_cached_list)
            )
        )
    }

}
