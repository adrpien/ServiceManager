package com.example.servicemanager.feature_authentication_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_authentication_domain.R
import com.example.servicemanager.feature_authentication_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Authenticate @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(mail: String, password: String): Flow<Resource<String>> {
        if (mail.isEmpty() || password.isEmpty()) {
            return flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        message = UiText.StringResource(R.string.e_mail_or_password_is_empty)
                    )
                )
            }
        } else {
            return userRepository.authenticate(mail.trim(), password.trim())
        }
    }
}