package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(inspectionId: String): Flow<com.example.core.util.Resource<Inspection>> {
        if(inspectionId.isEmpty()) {
            return flow {
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.ERROR,
                        Inspection(),
                        "Get inspection unknown error"
                    )
                )
            }
        }
        return repository.getInspection(inspectionId)
    }

}