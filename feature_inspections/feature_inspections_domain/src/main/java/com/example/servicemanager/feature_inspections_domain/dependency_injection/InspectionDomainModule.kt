package com.example.servicemanager.feature_inspections_domain.dependency_injection

import com.example.servicemanager.feature_inspections_domain.use_cases.SaveInspection
import com.example.servicemanager.feature_inspections_domain.use_cases.GetInspection
import com.example.servicemanager.feature_inspections_domain.use_cases.GetInspectionList
import com.example.servicemanager.feature_inspections_domain.use_cases.UpdateInspection
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections_domain.use_cases.InspectionUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InspectionDomainModule {


    @Provides
    @Singleton
    fun provideInspectionUseCases(
        repository: InspectionRepository
    ): InspectionUseCases {
        return InspectionUseCases(
            saveInspection = SaveInspection(repository),
            getInspection = GetInspection(repository),
            getInspectionList = GetInspectionList(repository),
            updateInspection = UpdateInspection(repository)
        )
    }

}


