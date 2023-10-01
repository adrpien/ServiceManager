package com.example.servicemanager.feature_authentication.domain.use_cases

import com.example.core.util.Resource
import com.example.servicemanager.feature_authentication.domain.model.User
import com.example.servicemanager.feature_authentication.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUser @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: String): Flow<com.example.core.util.Resource<User>> {
        if (userId.isBlank()) {
            return flow {  }
        } else {
            return userRepository.getUser(userId)
        }
    }
}