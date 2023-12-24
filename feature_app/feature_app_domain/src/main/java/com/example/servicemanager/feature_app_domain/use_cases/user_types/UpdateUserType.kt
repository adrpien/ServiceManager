package com.example.servicemanager.feature_app_domain.use_cases.user_types

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserType @Inject constructor (
    private val repository: AppRepository
) {

    operator fun invoke(userType: UserType): Flow<Resource<String>> {
        return if(userType.userTypeId != "") {
            repository.updateUserType(userType)
        } else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        "UserType update unknown error",
                        UiText.StringResource(R.string.usertype_update_unknown_error)
                    )
                )
            }
        }
    }

}