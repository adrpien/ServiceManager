package com.example.servicemanager.feature_inspections_domain

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class InspectionRepositoryFake : InspectionRepository {

    private var shouldReturnError = false

    private val inspectionList: MutableList<Inspection> = mutableListOf()

    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> = flow {

    }

    override fun getInspectionList(): Flow<Resource<List<Inspection>>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR, null))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspectionList))
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR, null))
        } else {
            inspectionList.add(inspection)
            emit(Resource(ResourceState.SUCCESS, inspection.inspectionId))
        }
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<String>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR, null))
        } else {
            val inspectionIndex =
                inspectionList.indexOf(inspectionList.find { it.inspectionId == inspection.inspectionId })
            inspectionList.removeAt(inspectionIndex)
            inspectionList.add(inspection)
            emit(Resource(ResourceState.SUCCESS, inspection.inspectionId))
        }
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR, null))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspectionList))
        }
    }

    fun setShouldReturnError(value: Boolean){
        shouldReturnError = value
    }
}