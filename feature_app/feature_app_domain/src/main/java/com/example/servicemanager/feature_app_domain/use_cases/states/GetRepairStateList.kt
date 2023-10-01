package com.example.servicemanager.feature_app.domain.use_cases.states

import com.example.servicemanager.feature_app.domain.model.RepairState
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepairStateList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<com.example.core.util.Resource<List<RepairState>>> {
        return repository.getRepairStateList()
    }
}