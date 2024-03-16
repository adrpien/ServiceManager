package com.example.servicemanager.common_domain.use_cases.hospitals

import com.example.core.util.Resource
import com.example.servicemanager.common_domain.model.Hospital
import com.example.servicemanager.common_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHospitalList @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(): Flow<Resource<List<Hospital>>> {
        return repository.getHospitalList()
    }

}