package com.example.servicemanager.feature_app_domain.dependency_injection

import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.signatures.GetSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.SaveSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.UpdateSignature
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetRepairStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDomainModule {
    @Provides
    @Singleton
    fun provideAppUseCases(repository: AppRepository): AppUseCases {
        return AppUseCases(
            getHospitalList = GetHospitalList(repository),
            saveSignature = SaveSignature(repository),
            getSignature = GetSignature(repository),
            updateSignature = UpdateSignature(repository),
            getEstStateList = GetEstStateList(repository),
            getInspectionStateList = GetInspectionStateList(repository),
            getRepairStateList = GetRepairStateList(repository),
            getTechnicianList = GetTechnicianList(repository)
        )
    }

}

