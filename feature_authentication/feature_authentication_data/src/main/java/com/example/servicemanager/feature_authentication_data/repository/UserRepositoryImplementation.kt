package com.example.servicemanager.feature_authentication_data.repository

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_authentication_data.R
import com.example.servicemanager.feature_authentication_data.remote.UserFirebaseApi
import com.example.servicemanager.feature_authentication_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class  UserRepositoryImplementation(
    val userFirebaseApi: UserFirebaseApi,
): UserRepository {
    override fun authenticate(
        mail: String,
        password: String,
    ): Flow<Resource<String>> {
    return userFirebaseApi.authenticate(mail, password)
    }

    override fun getCurrentUser(): Flow<Resource<String>> = flow {
        var data: String
        data = userFirebaseApi.getCurrentUser() ?: "0"
        if (data != "0"){
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    data,
                    UiText.StringResource(R.string.current_user_logged_in)
                )
            )
        } else {
            emit(
                Resource(
                    ResourceState.ERROR,
                    data,
                    UiText.StringResource(R.string.current_user_not_logged_in)
                )
            )
        }
    }

    override fun logOut() {
        return userFirebaseApi.logOut()
    }

}