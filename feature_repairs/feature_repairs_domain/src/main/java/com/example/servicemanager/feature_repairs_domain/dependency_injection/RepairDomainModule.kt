package com.example.servicemanager.feature_repairs_domain.dependency_injection


import com.adrpien.tiemed.domain.use_case.repairs.SaveRepair
import com.adrpien.tiemed.domain.use_case.repairs.GetRepair
import com.adrpien.tiemed.domain.use_case.repairs.GetRepairList
import com.adrpien.tiemed.domain.use_case.repairs.UpdateRepair
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


