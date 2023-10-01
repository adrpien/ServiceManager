package com.example.servicemanager.feature_app.domain.use_cases.hospitals

import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHospitalList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<com.example.core.util.Resource<List<Hospital>>> {
        return repository.getHospitalList()
    }

}