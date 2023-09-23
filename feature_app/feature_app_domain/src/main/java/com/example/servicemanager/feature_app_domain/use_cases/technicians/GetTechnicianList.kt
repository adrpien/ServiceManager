package com.example.servicemanager.feature_app.domain.use_cases.technicians

import com.example.servicemanager.feature_app.domain.model.Technician
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTechnicianList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<Resource<List<Technician>>> {
        return repository.getTechnicianList()
    }
}