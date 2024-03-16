package com.example.servicemanager.common_domain.use_cases.users

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.User
import com.example.servicemanager.common_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUser @Inject constructor(
    private val appRepository: AppRepository
) {

    operator fun invoke(userId: String): Flow<Resource<User>> {
        return try {
            appRepository.getUser(userId)
        } catch (e: IllegalArgumentException) {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        UiText.StringResource(R.string.unknown_error)
                    )
                )
            }

        }

    }
}