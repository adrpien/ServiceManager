package com.example.servicemanager.feature_authentication_domain.use_cases

import com.example.core.util.Resource
import com.example.servicemanager.feature_authentication_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Resource<String> {
        return userRepository.getCurrentUser()
    }
}
