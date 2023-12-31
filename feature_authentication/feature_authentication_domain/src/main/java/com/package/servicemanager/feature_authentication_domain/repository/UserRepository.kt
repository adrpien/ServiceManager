package com.example.servicemanager.feature_authentication_domain.repository

import com.example.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun authenticate(mail: String, password: String): Flow<Resource<String>>

    suspend fun getCurrentUser(): Resource<String>

    fun logOut()

}