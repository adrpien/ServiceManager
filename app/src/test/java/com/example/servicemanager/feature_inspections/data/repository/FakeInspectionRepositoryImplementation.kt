package com.example.servicemanager.feature_inspections.data.repository

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_inspections.domain.model.Inspection

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
        emit(Resource(ResourceState.LOADING, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList emits inspectionList"))
        if(returnErrorState) {
            emit(Resource(ResourceState.ERROR, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList emits inspectionList"))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList emits inspectionList"))
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> =  flow {
        remoteInspectionList.add(inspection)
        localInspectionList.removeAll(localInspectionList)
        localInspectionList.addAll(remoteInspectionList)
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<Boolean>> = flow {
        remoteInspectionList.remove(inspection)
        remoteInspectionList.add(inspection)
        localInspectionList.removeAll(localInspectionList)
        localInspectionList.addAll(remoteInspectionList)
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>>  = flow {
        val inspectionList: List<Inspection> = localInspectionList
        emit(Resource(ResourceState.SUCCESS, inspectionList, "FakeInspectionRepositoryImplementation: getInspectionList emits inspectionList from local"))
    }
}