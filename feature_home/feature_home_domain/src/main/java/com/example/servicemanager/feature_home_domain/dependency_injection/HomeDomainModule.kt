package com.example.servicemanager.feature_home_domain.dependency_injection

import com.example.servicemanager.feature_app_domain.repository.AppRepository
import com.example.servicemanager.feature_app_domain.use_cases.date_formatting_types.GetDateFormattingTypes
import com.example.servicemanager.feature_home_domain.use_cases.HomeUseCases
import com.example.servicemanager.feature_home_domain.use_cases.ImportInspectionsFromFile
import com.example.servicemanager.feature_home_domain.use_cases.SaveInspections
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
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
    fun provideHomeUseCases(inspectionRepository: InspectionRepository): HomeUseCases {
        return HomeUseCases(
            importInspectionsFromFile = ImportInspectionsFromFile(),
            saveInspections = SaveInspections(repository = inspectionRepository)
        )
    }
}