package com.example.servicemanager.common_domain.use_cases.user_types

import com.example.core.util.Resource
import com.example.servicemanager.common_domain.model.UserType
import com.example.servicemanager.common_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTypeList @Inject constructor (
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<Resource<List<UserType>>> {
        return repository.getUserTypeList()
    }
}