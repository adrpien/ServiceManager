package com.example.servicemanager.feature_inspections.data.repository

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection

import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow

class FakeInspectionRepositoryImplementation() : InspectionRepository {

    override fun getInspection(inspectionId: String): Flow<Resource<Inspection>> {
        TODO("Not yet implemented")
    }

    override fun getInspectionList(): Flow<Resource<List<Inspection>>> {
        TODO("Not yet implemented")
    }

    override fun insertInspection(inspection: Inspection): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun updateInspection(inspection: Inspection): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getInspectionListFromLocal(): Flow<Resource<List<Inspection>>> {
        TODO("Not yet implemented")
    }
}