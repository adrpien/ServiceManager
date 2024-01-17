package com.example.servicemanager.feature_inspections_domain

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InspectionRepositoryFake : InspectionRepository {

    private var shouldReturnError = false

    private val inspectionList: MutableList<Inspection> = mutableListOf()

    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR))
        } else {
            val inspection = inspectionList.find { it.inspectionId == inspectionId }
            emit(Resource(ResourceState.SUCCESS, inspection))
        }
    }

    override fun getInspectionList(): Flow<Resource<List<Inspection>>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspectionList))
        }
    }

    override suspend fun insertInspection(inspection: Inspection): Resource<String> {
        return if (shouldReturnError) {
            Resource(ResourceState.ERROR)
        } else {
            inspectionList.add(inspection)
            Resource(ResourceState.SUCCESS, inspection.inspectionId)
        }
    }

    override suspend fun updateInspection(inspection: Inspection): Resource<String> {
        return if (shouldReturnError) {
            Resource(ResourceState.ERROR)
        } else {
            val inspectionIndex =
                inspectionList.indexOf(inspectionList.find { it.inspectionId == inspection.inspectionId })
            inspectionList.removeAt(inspectionIndex)
            inspectionList.add(inspection)
            Resource(ResourceState.SUCCESS, inspection.inspectionId)
        }
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>> = flow {
        if (shouldReturnError) {
            emit(Resource(ResourceState.ERROR))
        } else {
            emit(Resource(ResourceState.SUCCESS, inspectionList))
        }
    }

    fun setShouldReturnError(value: Boolean){
        shouldReturnError = value
    }
}