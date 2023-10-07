package com.example.servicemanager.feature_app_domain.use_cases.users

import com.example.core.util.Resource
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUser @Inject constructor(
    private val appRepository: AppRepository
) {

    operator fun invoke(userId: String): Flow<Resource<User>> {
        if (userId.isBlank()) {
            return flow {  }
        } else {
            return appRepository.getUser(userId)
        }
    }
}