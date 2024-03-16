package com.example.servicemanager.common_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.logger.AppLogger
import com.example.servicemanager.common_data.local.local_image_source.LocalImageSource
import com.example.servicemanager.common_data.local.room.AppDatabase
import com.example.servicemanager.common_data.remote.AppFirebaseApi
import com.example.servicemanager.common_data.repository.AppRepositoryImplementation
import com.example.servicemanager.common_domain.repository.AppRepository

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
        localImageSource: LocalImageSource,
        appLogger: AppLogger<Any>,
    ): AppRepository {
        return AppRepositoryImplementation(
            appDatabase.appDatabaseDao,
            appFirebaseApi,
            appLogger,
            localImageSource
        )
    }



    @Provides
    @Singleton
    fun provideAppFirebaseApi(
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
    fun provideLocalImageSource(app: Application): LocalImageSource {
        return LocalImageSource(app)
    }
}

