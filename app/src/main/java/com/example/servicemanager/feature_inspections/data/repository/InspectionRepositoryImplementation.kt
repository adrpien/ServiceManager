package com.example.servicemanager.feature_inspections.data.repository


import android.util.Log
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_inspections.data.local.InspectionDatabaseDao
import com.example.servicemanager.feature_inspections.data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.*


class  InspectionRepositoryImplementation(
    val inspectionDatabaseDao: InspectionDatabaseDao,
    val inspectionFirebaseApi: InspectionFirebaseApi
): InspectionRepository {

    private val APP_REPOSITORY_IMPLEMENTATION = "APP REPOSITORY IMPLEMENTATION"


    /* ********************************* INSPECTIONS ******************************************** */
    override fun getInspectionList() = flow {
        emit(Resource(ResourceState.LOADING, null, "Inspection list fetching started"))
        var inspectionList: List<Inspection> = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        inspectionList.forEach { inspection ->

        }
        emit(Resource(ResourceState.LOADING, inspectionList, "Locally cached list"))
        val list = inspectionFirebaseApi.getInspectionList()?: emptyList()
        if(list.isNotEmpty()) {
            inspectionDatabaseDao.deleteAllInspections()
            for (device in list) {
                inspectionDatabaseDao.insertInspection(device.toInspectionEntity())
            }
            inspectionList = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
            emit(Resource(ResourceState.SUCCESS, inspectionList, "Device list fetching finished"))
        }
    }


    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> = flow{
        emit(Resource(ResourceState.LOADING, null, "Inspection record fetching started"))
        var inspection = inspectionDatabaseDao.getInspection(inspectionId).toInspection()
        emit(Resource(ResourceState.LOADING, inspection, "Locally cached record"))
        val record = inspectionFirebaseApi.getInspection(inspectionId)
        if(record != null) {
            inspectionDatabaseDao.deleteInspection(inspectionId)
            inspectionDatabaseDao.insertInspection(record.toInspectionEntity())
            inspection = inspectionDatabaseDao.getInspection(inspectionId).toInspection()
            emit(Resource(ResourceState.SUCCESS, inspection, "Inspection record fetching finished"))
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> {
        return inspectionFirebaseApi.createInspection(inspection)
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<Boolean>> {
        return inspectionFirebaseApi.updateInspection(inspection)
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>> = flow {
        emit(Resource(ResourceState.LOADING, null, "Inspection list fetching form local started"))
        var inspectionList: List<Inspection> = inspectionDatabaseDao.getInspectionList().map { it.toInspection() }
        emit(Resource(ResourceState.SUCCESS, inspectionList, "Locally storaged list"))
    }

}
