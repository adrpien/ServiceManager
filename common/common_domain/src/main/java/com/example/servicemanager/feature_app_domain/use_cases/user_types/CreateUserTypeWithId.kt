package com.example.servicemanager.feature_app_domain.use_cases.user_types

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import javax.inject.Inject

class CreateUserTypeWithId @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(userType: UserType): Resource<String> {
        return try {
            repository.createUserTypeWithId(userType)
        } catch (e: IllegalArgumentException) {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }

}