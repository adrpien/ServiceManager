package com.adrpien.tiemed.domain.use_case.inspections

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateInspection @Inject constructor (
    private val repository: InspectionRepository
) {
    operator fun invoke(inspection: Inspection): Flow<Resource<Boolean>> {
        return repository.insertInspection(inspection)
    }
}