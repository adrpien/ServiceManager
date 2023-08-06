package com.example.servicemanager.feature_user.data.repository

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_user.data.local.UserDatabaseDao
import com.example.servicemanager.feature_user.data.remote.UserFirebaseApi
import com.example.servicemanager.feature_user.domain.model.User
import com.example.servicemanager.feature_user.domain.repository.UserRepository
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
}