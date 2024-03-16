package com.example.servicemanager.common_domain.use_cases.technicians

import com.example.servicemanager.common_domain.model.Technician
import com.example.servicemanager.common_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTechnicianList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<com.example.core.util.Resource<List<Technician>>> {
        return repository.getTechnicianList()
    }
}