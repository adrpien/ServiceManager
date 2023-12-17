package com.example.servicemanager.feature_home_data.dependency_injection

import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeDataModule {

    @Provides
    @Singleton
    fun providesHomeUseCases(appRepository: AppRepository): HomeUseCases {
        return HomeUseCases(
        )
    }
}


