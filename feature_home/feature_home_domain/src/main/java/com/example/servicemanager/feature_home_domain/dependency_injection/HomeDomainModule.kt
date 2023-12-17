package com.example.servicemanager.feature_home_domain.dependency_injection

import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.example.servicemanager.feature_home_domain.use_cases.GetUser
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeDomainModule {
    @Provides
    @Singleton
    fun provideHomeUseCases(appRepository: AppRepository): HomeUseCases {
        return HomeUseCases(
            getUser = GetUser(appRepository = appRepository),
        )
    }
}