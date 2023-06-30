package com.example.servicemanager.dependecy_injection

import android.app.Application
import androidx.room.Room
import com.adrpien.tiemed.data.local.AppDatabase
import com.example.servicemanager.feature_app.data.remote.AppFirebaseApi
import com.example.servicemanager.feature_app.data.repository.AppRepositoryImplementation
import com.example.servicemanager.feature_app.domain.repository.AppRepository
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
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun ProvideFirebaseStorage(): FirebaseStorage {
            return FirebaseStorage.getInstance()
        }

        @Provides
        @Singleton
        fun ProvideFirebaseAuthentication(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun ProvideFirebaseFirestore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

        @Provides
        @Singleton
        fun ProvideFirebaseApi(
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
        fun ProvideTiemedDatabase(app: Application): AppDatabase {
            return Room.databaseBuilder(
                app,
                AppDatabase::class.java,
                "tiemed_db"
            ).build()
        }

        @Provides
        @Singleton
        fun ProvideRepository(
            appDatabase: AppDatabase,
            appFirebaseApi: AppFirebaseApi
        ): AppRepository {
            return AppRepositoryImplementation(
                appDatabase.appDatabaseDao,
                appFirebaseApi
            )
        }
    }

    @Binds
    @Singleton
    abstract fun ProvideRepository(
        RepairRepositoryImplementation: AppRepositoryImplementation
    ): AppRepositoryImplementation
}