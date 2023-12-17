package com.example.servicemanager.feature_authentication_domain.dependency_injection

import com.example.servicemanager.feature_authentication_domain.repository.UserRepository
import com.example.servicemanager.feature_authentication_domain.use_cases.Authenticate
import com.example.servicemanager.feature_authentication_domain.use_cases.GetCurrentUser
import com.example.servicemanager.feature_authentication_domain.use_cases.AuthenticationUseCases
import com.servicemanager.feature_authentication_domain.use_cases.LogOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationDomainModule {
    @Provides
    @Singleton
    fun provideUserUseCases(userRepository: UserRepository): AuthenticationUseCases {
        return AuthenticationUseCases(
            getCurrentUser = GetCurrentUser(userRepository),
            authenticate = Authenticate(userRepository),
            logOut = LogOut(userRepository)
        )
    }
}