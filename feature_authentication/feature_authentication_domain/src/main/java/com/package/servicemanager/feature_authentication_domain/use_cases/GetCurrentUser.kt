package com.example.servicemanager.feature_authentication.domain.use_cases

import com.example.core.util.Resource
import com.example.servicemanager.feature_authentication.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<String>> {
        return userRepository.getCurrentUser()
    }
}
