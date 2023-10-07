package com.example.servicemanager.feature_app_domain.use_cases.states


import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInspectionStateList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<com.example.core.util.Resource<List<InspectionState>>> {
        return repository.getInspectionStateList()
    }

}