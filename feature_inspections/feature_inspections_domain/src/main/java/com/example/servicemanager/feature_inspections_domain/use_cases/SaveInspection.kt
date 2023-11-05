package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveInspection @Inject constructor (
    private val repository: InspectionRepository
) {
    operator fun invoke(inspection: Inspection): Flow<Resource<String>> {
        return if (inspection.deviceSn.isNotEmpty() && inspection.deviceIn.isNotEmpty()) {
            repository.insertInspection(inspection)
        } else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "TextFields deviceSn and deviceIn are empty",
                        "TextFields deviceSn and deviceIn are empty"
                    )
                )
            }
        }
    }
}