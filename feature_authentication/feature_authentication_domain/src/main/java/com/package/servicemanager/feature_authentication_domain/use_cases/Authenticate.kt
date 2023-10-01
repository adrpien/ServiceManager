package com.example.servicemanager.feature_authentication.domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_authentication.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Authenticate @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(mail: String, password: String): Flow<com.example.core.util.Resource<String>> {
        if (mail.isEmpty() || password.isEmpty()) {
            return flow {
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.ERROR,
                        "E-mail or password textfield is empty"
                    )
                )
            }
        } else {
            return userRepository.authenticate(mail.trim(), password.trim())
        }
    }
}