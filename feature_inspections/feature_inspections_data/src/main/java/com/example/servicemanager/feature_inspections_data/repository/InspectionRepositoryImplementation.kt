package com.example.servicemanager.feature_inspections_data.repository


import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabaseDao
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_data.mappers.toInspectionEntity
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import kotlinx.coroutines.flow.*


class  InspectionRepositoryImplementation(
    val inspectionDatabaseDao: InspectionDatabaseDao,
    val inspectionFirebaseApi: InspectionFirebaseApi
): InspectionRepository {

    private val APP_REPOSITORY_IMPLEMENTATION = "APP REPOSITORY IMPLEMENTATION"


    /* ********************************* INSPECTIONS ******************************************** */
    override fun getInspectionList() = flow {
        var inspectionList: List<Inspection> = listOf<Inspection>()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                inspectionList,
                "Inspection list fetching started"
            )
        )
        inspectionList = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                inspectionList,
                "Locally cached list"
            )
        )
        val list = inspectionFirebaseApi.getInspectionList()?: emptyList()
        if(list.isNotEmpty()) {
            inspectionDatabaseDao.deleteAllInspections()
            for (device in list) {
                inspectionDatabaseDao.insertInspection(device.toInspectionEntity())
            }
            inspectionList = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    inspectionList,
                    "Device list fetching finished"
                )
            )
        }
    }


    override fun getInspection(inspectionId: String): Flow<com.example.core.util.Resource<Inspection>> = flow{
        var inspection = Inspection()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                inspection,
                "Inspection record fetching started"
            )
        )
        inspection = inspectionDatabaseDao.getInspection(inspectionId).toInspection()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
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
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    inspection,
                    "Inspection record fetching finished"
                )
            )
        } else {
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.ERROR,
                    inspection,
                    "Inspection record fetching error"
                )
            )
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<com.example.core.util.Resource<String>> {
        return inspectionFirebaseApi.createInspection(inspection)
    }

    override fun updateInspection(inspection: Inspection): Flow<com.example.core.util.Resource<String>> {
        return inspectionFirebaseApi.updateInspection(inspection)
    }

    override fun getInspectionListFromLocal(): Flow<com.example.core.util.Resource<List<Inspection>>> = flow {
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                null,
                "Inspection list fetching form local started"
            )
        )
        var inspectionList: List<Inspection> = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.SUCCESS,
                inspectionList,
                "Locally storaged list"
            )
        )
    }

}
