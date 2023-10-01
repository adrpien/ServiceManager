package com.example.servicemanager.feature_inspections_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.adrpien.tiemed.domain.use_case.inspections.SaveInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.adrpien.tiemed.domain.use_case.inspections.UpdateInspection
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InspectionDataModule {

    @Provides
    @Singleton
    fun provideInspectionFirebaseApi(
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): InspectionFirebaseApi {
        return InspectionFirebaseApi(
            firebaseFirestore = firebaseFirestore,
            firebaseStorage = firebaseStorage
        )
    }

    @Provides
    @Singleton
    fun provideInspectionDatabase(app: Application): InspectionDatabase {
        return Room.databaseBuilder(
            app,
            InspectionDatabase::class.java,
            "inspection_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideInspectionRepository(
        inspectionDatabase: InspectionDatabase,
        inspectionFirebaseApi: InspectionFirebaseApi
    ): InspectionRepository {
        return InspectionRepositoryImplementation(
            inspectionDatabase.inspectionDatabaseDao,
            inspectionFirebaseApi
        )
    }

}


