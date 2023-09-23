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
    ): Flow<Resource<String>> {
    return userFirebaseApi.authenticate(mail, password)
    }

    // I think holding user data in room is not safe, UserDatabase is not used for now
    override fun getUser(userId: String): Flow<Resource<User>> = flow {
        var user: User? = User()
        emit(Resource(ResourceState.LOADING, user, "User fetching started"))
        user = userFirebaseApi.getUser(userId)
        if (user != null){
            emit(Resource(ResourceState.SUCCESS, user, "User fetching finished"))
        } else {
            emit(Resource(ResourceState.ERROR, user, "User fetching error"))
        }
    }

    override fun getCurrentUser(): Flow<Resource<String>> = flow {
        var data: String  = ""
        emit(Resource(ResourceState.LOADING, data, "Current user fetching started"))
        data = userFirebaseApi.getCurrentUser() ?: "0"
        if (data != null){
            emit(Resource(ResourceState.SUCCESS, data, "Current user logged in"))
        } else {
            emit(Resource(ResourceState.ERROR, "Current user not logged in", "Current user not logged in"))
        }
    }

}