package com.adrpien.tiemed.domain.use_case.inspections

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(inspectionId: String): Flow<Resource<Inspection>> {
        if(inspectionId.isBlank()) {
            return flow {  }
        }
        return repository.getInspection(inspectionId)
    }

}