package com.example.servicemanager.feature_repairs_domain.dependency_injection


import com.example.servicemanager.feature_repairs_domain.use_cases.SaveRepair
import com.example.servicemanager.feature_repairs_domain.use_cases.GetRepair
import com.example.servicemanager.feature_repairs_domain.use_cases.GetRepairList
import com.example.servicemanager.feature_repairs_domain.use_cases.UpdateRepair
import com.example.servicemanager.feature_repairs_domain.repository.RepairRepository
import com.example.servicemanager.feature_repairs_domain.use_cases.RepairUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepairDomainModule {
    @Provides
    @Singleton
    fun provideRepairUseCases(
        repository: RepairRepository
    ): RepairUseCases {
        return RepairUseCases(
            saveRepair = SaveRepair(repository),
            getRepair = GetRepair(repository),
            getRepairList = GetRepairList(repository),
            updateRepair = UpdateRepair(repository)
        )
    }
}


