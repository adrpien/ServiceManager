package com.adrpien.tiemed.domain.use_case.inspections

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(inspection: Inspection): Flow<Resource<String>> {
        return if(inspection.inspectionId == "0") repository.updateInspection(inspection)
        else {
            flow<Resource<String>> {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "Inspection update unknown error",
                        "Inspection update unknown error"
                    )
                )
            }
        }
    }
}