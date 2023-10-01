package com.example.servicemanager.feature_inspections_domain.dependency_injection

import com.adrpien.tiemed.domain.use_case.inspections.SaveInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.adrpien.tiemed.domain.use_case.inspections.UpdateInspection
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
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


