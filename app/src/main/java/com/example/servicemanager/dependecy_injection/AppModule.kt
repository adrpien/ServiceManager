package com.example.servicemanager.dependecy_injection

import android.app.Application
import androidx.room.Room
import com.adrpien.tiemed.data.local.AppDatabase
import com.adrpien.tiemed.domain.use_case.inspections.GetInspectionList
import com.example.servicemanager.feature_app.data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app.data.repository.AppRepositoryImplementation
import com.example.servicemanager.feature_app.domain.repository.AppRepository
import com.example.servicemanager.feature_app.domain.use_cases.AppUseCases
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.signatures.CreateSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.GetSignature
import com.example.servicemanager.feature_app.domain.use_cases.signatures.UpdateSignature
import com.example.servicemanager.feature_app.domain.use_cases.states.GetEstStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetInspectionStateList
import com.example.servicemanager.feature_app.domain.use_cases.states.GetRepairStateList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
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
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAppFirebaseApi(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): AppFirebaseApi {
        return AppFirebaseApi(
            firebaseFirestore = firebaseFirestore,
            firebaseStorage = firebaseStorage
        )
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "tiemed_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        appDatabase: AppDatabase,
        appFirebaseApi: AppFirebaseApi
    ): AppRepository {
        return AppRepositoryImplementation(
            appDatabase.appDatabaseDao,
            appFirebaseApi
        )
    }

    @Provides
    @Singleton
    fun provideAppUseCases(repository: AppRepository): AppUseCases {
        return AppUseCases(
            getHospitalList = GetHospitalList(repository),
            createSignature = CreateSignature(repository),
            getSignature = GetSignature(repository),
            updateSignature = UpdateSignature(repository),
            getEstStateList = GetEstStateList(repository),
            getInspectionStateList = GetInspectionStateList(repository),
            getRepairStateList = GetRepairStateList(repository),
            getTechnicianList = GetTechnicianList(repository)
        )
    }
}

