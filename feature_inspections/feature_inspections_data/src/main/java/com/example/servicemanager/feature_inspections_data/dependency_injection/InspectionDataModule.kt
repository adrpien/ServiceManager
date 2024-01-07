package com.example.servicemanager.feature_inspections_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.caching_domain.use_cases.CachingUseCases
import com.example.logger.AppLogger
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabase
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections_data.repository.InspectionRepositoryImplementation
import com.example.servicemanager.feature_inspections_domain.repository.InspectionRepository
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
        firebaseStorage: FirebaseStorage,
        // cachingUseCases: CachingUseCases
    ): InspectionFirebaseApi {
        return InspectionFirebaseApi(
            firebaseFirestore = firebaseFirestore,
            firebaseStorage = firebaseStorage,
            // cachingUseCases = cachingUseCases
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
        inspectionFirebaseApi: InspectionFirebaseApi,
        appLogger: AppLogger<Any>,
        cachingUseCases: CachingUseCases
    ): InspectionRepository {
        return InspectionRepositoryImplementation(
            inspectionDatabase.inspectionDatabaseDao,
            inspectionFirebaseApi,
            appLogger,
        )
    }

}


