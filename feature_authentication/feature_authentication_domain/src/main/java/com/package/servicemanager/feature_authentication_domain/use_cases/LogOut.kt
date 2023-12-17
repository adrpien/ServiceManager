package com.servicemanager.feature_authentication_domain.use_cases

import com.example.core.util.Resource
import com.example.servicemanager.feature_authentication_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogOut @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() {
        userRepository.logOut()
    }
}