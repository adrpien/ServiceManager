package com.example.servicemanager.feature_app_domain.use_cases.repair_states

import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepairStateList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<Resource<List<RepairState>>> {
        return repository.getRepairStateList()
    }
}