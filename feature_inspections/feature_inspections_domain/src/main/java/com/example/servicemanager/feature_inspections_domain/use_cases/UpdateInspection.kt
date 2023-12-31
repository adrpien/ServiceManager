package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_domain.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    suspend operator fun invoke(inspection: Inspection): Resource<String> {
        if(inspection.inspectionId != ""){
            return repository.updateInspection(inspection)
        }
        else {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.inspection_update_unknown_error)
            )
        }
    }
}