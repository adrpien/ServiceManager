package com.example.servicemanager.feature_user.domain.use_cases

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_user.domain.model.User
import com.example.servicemanager.feature_user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<String>> {
        return userRepository.getCurrentUser()
    }
}
