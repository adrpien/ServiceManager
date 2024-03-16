package com.example.servicemanager.feature_inspections_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_data.R
import com.example.logger.AppLogger
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabaseDao
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_data.mappers.toInspectionEntity
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.*


class  InspectionRepositoryImplementation(
    private val inspectionDatabaseDao: InspectionDatabaseDao,
    private val inspectionFirebaseApi: InspectionFirebaseApi,
    private val appLogger: AppLogger<Any>,
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
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.inspection_list_fetching_error)
                )
            )
        }
    }

    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> = flow{
        if (inspectionId == "") throw IllegalArgumentException("inspectionId can not be empty")
        var inspection: Inspection?
        inspection = inspectionDatabaseDao.getInspection(inspectionId)?.toInspection()
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
            inspection = inspectionDatabaseDao.getInspection(inspectionId)?.toInspection()
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    inspection,
                    UiText.StringResource(R.string.inspection_record_fetching_finished)
                )
            )
        } else {
            throw NoSuchElementException()
        }
    }

    override suspend fun insertInspection(inspection: Inspection): Resource<String> {
        if(inspection.inspectionId == "") {
            throw IllegalArgumentException("inspectionId can not be empty")
        }
        appLogger.logEvent(
            eventLogType = com.example.logger.EventLogType.NewRecordLog(),
            dataClassObject = inspection,
        )
        return inspectionFirebaseApi.createInspection(inspection)
    }

    override suspend fun updateInspection(inspection: Inspection): Resource<String> {
        if(inspection.inspectionId == "") {
            throw IllegalArgumentException("inspectionId can not be empty")
        }
        appLogger.logEvent(
            eventLogType = com.example.logger.EventLogType.RecordUpdateLog(),
            dataClassObject = inspection,
            )
        return inspectionFirebaseApi.updateInspection(inspection)
    }

    override suspend  fun getInspectionListFromLocal(): Resource<List<Inspection>> {
        val inspectionList: List<Inspection> = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        return Resource(
            ResourceState.SUCCESS,
            inspectionList,
            UiText.StringResource(R.string.locally_cached_list)
        )
    }

}
