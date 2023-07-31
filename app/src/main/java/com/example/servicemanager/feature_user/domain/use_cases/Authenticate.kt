package com.example.servicemanager.feature_user.domain.use_cases

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_user.domain.model.User
import com.example.servicemanager.feature_user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Authenticate @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(mail: String, password: String): Flow<Resource<String>> {
        if (mail.isBlank() || password.isBlank()) {
            return flow {  }
        } else {
            return userRepository.authenticate(mail, password)
        }
    }
}