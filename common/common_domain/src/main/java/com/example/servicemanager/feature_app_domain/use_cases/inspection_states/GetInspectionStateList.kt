package com.example.servicemanager.feature_app_domain.use_cases.inspection_states


import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInspectionStateList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<Resource<List<InspectionState>>> {
        return repository.getInspectionStateList()
    }

}