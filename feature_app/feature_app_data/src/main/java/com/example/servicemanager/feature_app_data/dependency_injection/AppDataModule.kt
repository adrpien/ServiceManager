package com.example.servicemanager.feature_app_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.logger.AppLogger
import com.example.servicemanager.feature_app_data.local.AppDatabase
import com.example.servicemanager.feature_app_data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app_data.repository.AppRepositoryImplementation
import com.example.servicemanager.feature_app_domain.repository.AppRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDataModule {
    @Provides
    @Singleton
    fun provideAppRepository(
        appDatabase: AppDatabase,
        appFirebaseApi: AppFirebaseApi,
        appLogger: com.example.logger.AppLogger
    ): AppRepository {
        return AppRepositoryImplementation(
            appDatabase.appDatabaseDao,
            appFirebaseApi,
            appLogger
        )
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
}

