package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import javax.inject.Inject

class CacheInspection @Inject constructor (
    private val repository: InspectionRepository
) {
    suspend operator fun invoke(inspection: Inspection): Resource<Inspection> {

        return Resource (
            ResourceState.SUCCESS,
            inspection,
            UiText.DynamicString("empty implementation")
        )

    }
}