package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_inspections_domain.R
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import javax.inject.Inject

class UpdateInspection @Inject constructor (
    private val repository: InspectionRepository
) {

    suspend operator fun invoke(inspection: Inspection): Resource<String> {
        return try {
            if (inspection.inspectionDate.any{ !it.isDigit() }) {
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.wrong_date_format)
                )
            }
            if (inspection.deviceSn != "" && inspection.deviceIn != "") {
                return repository.updateInspection(inspection)
            } else {
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.sn_and_in_can_not_be_empty)
                )
            }

        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }

    }
}