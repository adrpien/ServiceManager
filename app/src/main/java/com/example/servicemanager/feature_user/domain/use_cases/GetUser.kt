package com.example.servicemanager.feature_user.domain.use_cases

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_user.domain.model.User
import com.example.servicemanager.feature_user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUser @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: String): Flow<Resource<User>> {
        if (userId.isBlank()) {
            return flow {  }
        } else {
            return userRepository.getUser(userId)
        }
    }
}