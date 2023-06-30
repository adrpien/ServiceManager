package com.adrpien.tiemed.domain.use_case.inspections


import androidx.room.util.query
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInspectionList @Inject constructor (
    private val repository: InspectionRepository
) {

    operator fun invoke(searchQuery: String): Flow<Resource<List<Inspection>>> {
        // TODO searchQuery in GetInspectionList use case to implement
        return repository.getInspectionList()
    }
}