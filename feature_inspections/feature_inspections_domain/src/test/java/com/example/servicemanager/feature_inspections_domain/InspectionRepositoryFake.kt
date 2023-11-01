package com.example.servicemanager.feature_inspections_domain

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class InspectionRepositoryFake : InspectionRepository {

    private var shouldReturnError = false

    private val  inspectionList = mutableListOf<Inspection>()
    private val  inspectionListFlow = MutableStateFlow(
        Resource(ResourceState.SUCCESS, inspectionList)
    )

    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> {
        val inspection = inspectionList.find { it.inspectionId == inspectionId }
        return if (shouldReturnError) {
            flow { Resource(ResourceState.ERROR, null) }
        } else {
            flow { Resource(ResourceState.SUCCESS, inspection) }
        }
    }

    override fun getInspectionList(): Flow<Resource<List<Inspection>>> {
        return if (shouldReturnError) {
            inspectionListFlow.value = Resource(ResourceState.ERROR, null)
            inspectionListFlow
        } else {
            inspectionListFlow
        }
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> {
        inspectionList.add(inspection)
        inspectionListFlow.value = Resource(ResourceState.SUCCESS, inspectionList)
        return flow { Resource(ResourceState.SUCCESS, inspection.inspectionId) }
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<String>> {
        return if (shouldReturnError) {
            flow { Resource(ResourceState.ERROR, null) }
        } else {
            val inspectionIndex =
                inspectionList.indexOf(inspectionList.find { it.inspectionId == inspection.inspectionId })
            inspectionList.removeAt(inspectionIndex)
            inspectionList.add(inspection)
            flow {
                Resource(ResourceState.SUCCESS, inspection.inspectionId)
            }
        }
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>> {
        return inspectionListFlow
    }

    fun setShouldReturnError(value: Boolean){
        shouldReturnError = value
    }
}