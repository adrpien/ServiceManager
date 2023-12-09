package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState.*
import com.example.core.util.UiText
import com.example.feature_inspections_domain.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_domain.util.InspectionListExtensionFunctions.Companion.orderInspectionList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(inspectionId: String): Flow<Resource<Inspection>> {
        return if(inspectionId.isEmpty()) {
            flow {
                emit(
                    Resource(
                        ERROR,
                        Inspection(),
                        UiText.StringResource(R.string.get_inspection_unknown_error)
                    )
                )
            }
        } else {
            repository.getInspection(inspectionId)
        }
    }
}

