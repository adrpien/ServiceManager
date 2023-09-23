package com.example.dependency_injection

import android.app.Application
import androidx.room.Room
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.adrpien.tiemed.domain.use_case.inspections.SaveInspection
import com.adrpien.tiemed.domain.use_case.inspections.UpdateInspection
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
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabase
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections_data.repository.InspectionRepositoryImplementation
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    @Named("inspection_test_db")
    fun provideInspectionDatabase(app: Application): InspectionDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            InspectionDatabase::class.java
        ).allowMainThreadQueries().build()
    }

}