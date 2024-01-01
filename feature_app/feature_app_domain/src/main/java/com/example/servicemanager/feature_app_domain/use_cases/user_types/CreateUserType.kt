package com.example.servicemanager.feature_app_domain.use_cases.user_types

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateUserType @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(userType: UserType): Resource<String> {
        return if(userType.userTypeName != "") {
            repository.createUserType(userType)
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.usertype_name_can_not_be_empty)
            )
        }
    }

}