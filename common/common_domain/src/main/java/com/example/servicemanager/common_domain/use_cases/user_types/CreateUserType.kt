package com.example.servicemanager.common_domain.use_cases.user_types

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.common_domain.R
import com.example.servicemanager.common_domain.model.UserType
import com.example.servicemanager.common_domain.repository.AppRepository
import javax.inject.Inject

class CreateUserType @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(userType: UserType): Resource<String> {
        return try {
            repository.createUserType(userType)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}