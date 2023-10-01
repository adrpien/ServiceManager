package com.example.servicemanager.feature_authentication.domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun authenticate(mail: String, password: String): Flow<com.example.core.util.Resource<String>>

    fun getUser(userId: String): Flow<com.example.core.util.Resource<User>>

    fun getCurrentUser(): Flow<com.example.core.util.Resource<String>>


}