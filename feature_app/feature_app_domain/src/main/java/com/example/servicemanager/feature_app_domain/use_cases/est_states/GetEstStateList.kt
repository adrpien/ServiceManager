package com.example.servicemanager.feature_app_domain.use_cases.est_states

import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEstStateList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<Resource<List<EstState>>> {
        return repository.getEstStateList()
    }

}