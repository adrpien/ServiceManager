package com.example.servicemanager.feature_authentication.data.repository

import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_authentication.data.local.UserDatabaseDao
import com.example.servicemanager.feature_authentication.data.remote.UserFirebaseApi
import com.example.servicemanager.feature_authentication.domain.model.User
import com.example.servicemanager.feature_authentication.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserRepositoryImplementation(
    val userFirebaseApi: UserFirebaseApi,
    val userDatabaseDao: UserDatabaseDao
): UserRepository {
    override fun authenticate(
        mail: String,
        password: String,
    ): Flow<com.example.core.util.Resource<String>> {
    return userFirebaseApi.authenticate(mail, password)
    }

    // I think holding user data in room is not safe, UserDatabase is not used for now
    override fun getUser(userId: String): Flow<com.example.core.util.Resource<User>> = flow {
        var user: User? = User()
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                user,
                "User fetching started"
            )
        )
        user = userFirebaseApi.getUser(userId)
        if (user != null){
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    user,
                    "User fetching finished"
                )
            )
        } else {
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.ERROR,
                    user,
                    "User fetching error"
                )
            )
        }
    }

    override fun getCurrentUser(): Flow<com.example.core.util.Resource<String>> = flow {
        var data: String  = ""
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                data,
                "Current user fetching started"
            )
        )
        data = userFirebaseApi.getCurrentUser() ?: "0"
        if (data != null){
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.SUCCESS,
                    data,
                    "Current user logged in"
                )
            )
        } else {
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.ERROR,
                    "Current user not logged in",
                    "Current user not logged in"
                )
            )
        }
    }

}