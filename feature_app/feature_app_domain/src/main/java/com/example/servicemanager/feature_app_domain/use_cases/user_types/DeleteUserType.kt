package com.example.servicemanager.feature_app_domain.use_cases.user_types

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_app_domain.R
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteUserType @Inject constructor (
    private val repository: AppRepository
) {

    suspend operator fun invoke(userType: UserType): Resource<String> {
        return if(userType.userTypeId != "") {
            repository.deleteHospital(userType.userTypeId)
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.usertype_delete_unknown_error)
            )
        }
    }

}