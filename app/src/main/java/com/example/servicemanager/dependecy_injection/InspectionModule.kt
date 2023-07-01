package com.example.servicemanager.dependecy_injection

import android.app.Application
import androidx.room.Room
import com.adrpien.tiemed.data.local.AppDatabase
import com.adrpien.tiemed.domain.use_case.inspections.CreateInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspection
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.adrpien.tiemed.domain.use_case.inspections.UpdateInspection
import com.example.servicemanager.feature_app.data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app.data.repository.AppRepositoryImplementation
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.feature_inspections.data.local.InspectionDatabase
import com.example.servicemanager.feature_inspections.data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections.data.repository.InspectionRepositoryImplementation
import com.example.servicemanager.feature_inspections.domain.repository.InspectionRepository
import com.example.servicemanager.feature_inspections.domain.use_cases.InspectionUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InspectionModule {
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

    @Provides
    @Singleton
    fun provideInspectionUseCases(
        repository: InspectionRepository
    ): InspectionUseCases {
        return InspectionUseCases(
            createInspection = CreateInspection(repository),
            getInspection = GetInspection(repository),
            getInspectionList = GetInspectionList(repository),
            updateInspection = UpdateInspection(repository)
        )
    }
}


