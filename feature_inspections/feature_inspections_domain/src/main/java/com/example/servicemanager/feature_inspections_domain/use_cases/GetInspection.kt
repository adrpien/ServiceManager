package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState.*
import com.example.core.util.UiText
import com.example.feature_inspections_domain.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(inspectionId: String): Flow<Resource<Inspection>> {
        return try {
            repository.getInspection(inspectionId)
        } catch (e: IllegalArgumentException) {
            flow {
                emit(
                    Resource(
                        ERROR,
                        Inspection(),
                        UiText.StringResource(R.string.unknown_error)
                    )
                )
            }
        }
    }
}

