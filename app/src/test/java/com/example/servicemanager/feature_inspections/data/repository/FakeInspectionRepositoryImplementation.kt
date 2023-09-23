package com.example.servicemanager.feature_inspections.data.repository

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.model.Inspection

import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeInspectionRepositoryImplementation() : InspectionRepository {

    private var returnErrorState: Boolean = false

    private val localInspectionList = mutableListOf<Inspection>()
    private val remoteInspectionList = mutableListOf<Inspection>()

    private fun setResourceState(errorState: Boolean) {
        returnErrorState = errorState
    }


    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> =  flow {
        val inspection: Inspection = localInspectionList.find { it.inspectionId == inspectionId } ?: Inspection (inspectionId = "0")
        emit(Resource(ResourceState.LOADING, inspection, "FakeInspectionRepositoryImplementation: getInspection emits loading status"))
        if(returnErrorState) {
            emit(Resource(ResourceState.ERROR, inspection, "FakeInspectionRepositoryImplementation: getInspection emits error status"))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspection, "FakeInspectionRepositoryImplementation: getInspection emits success status"))
        }
    }

    override fun getInspectionList(): Flow<Resource<List<Inspection>>> = flow {
        val inspectionList: List<Inspection> = localInspectionList
        emit(Resource(ResourceState.LOADING, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList loading"))
        if(returnErrorState) {
            emit(Resource(ResourceState.ERROR, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList error"))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList success"))
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> =  flow {
        emit(Resource(ResourceState.LOADING, "0", "FakeInspectionRepositoryImplementation: insert inspection loading"))
        if (returnErrorState) {
            emit(Resource(ResourceState.ERROR, "0", "FakeInspectionRepositoryImplementation: insert inspection error"))
        } else {
            remoteInspectionList.add(inspection)
            localInspectionList.removeAll(localInspectionList)
            localInspectionList.addAll(remoteInspectionList)
            emit(Resource(ResourceState.SUCCESS, "0", "FakeInspectionRepositoryImplementation: insert inspection success"))
        }

    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<String>> = flow {
        emit(Resource(ResourceState.LOADING, "0", "FakeInspectionRepositoryImplementation: update inspection loading"))
        if (returnErrorState) {
            emit(Resource(ResourceState.ERROR, "0", "FakeInspectionRepositoryImplementation: update inspection error"))
        } else {
            remoteInspectionList.remove(inspection)
            remoteInspectionList.add(inspection)
            localInspectionList.removeAll(localInspectionList)
            localInspectionList.addAll(remoteInspectionList)
            emit(Resource(ResourceState.SUCCESS, "0", "FakeInspectionRepositoryImplementation: update inspection success"))
        }

    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>>  = flow {
        val inspectionList: List<Inspection> = localInspectionList
        emit(Resource(ResourceState.SUCCESS, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList emits inspectionList from local"))
    }
}