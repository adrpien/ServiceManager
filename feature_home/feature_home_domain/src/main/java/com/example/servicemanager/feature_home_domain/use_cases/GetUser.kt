package com.example.servicemanager.feature_home_domain.use_cases

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_home_domain.R
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUser @Inject constructor(
    private val appRepository: AppRepository
) {
    operator fun invoke(profileId: String): Flow<Resource<User>> {
        return if(profileId != "") appRepository.getUser(profileId)
        else {
            flow {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        null,
                        UiText.StringResource(R.string.getuser_uknown_error)
                        )
                )
            }
        }
    }
}