package com.example.servicemanager.feature_user.domain.repository

import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.feature_user.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun authenticate(mail: String, password: String): Flow<Resource<String>>

    fun getUser(userId: String): Flow<Resource<User>>

    fun getCurrentUser(): Flow<Resource<String>>


}