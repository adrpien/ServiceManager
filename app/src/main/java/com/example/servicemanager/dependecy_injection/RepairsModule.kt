package com.example.servicemanager.dependecy_injection

import android.app.Application
import androidx.room.Room
import com.adrpien.tiemed.data.local.TiemedDatabase
import com.adrpien.tiemed.data.remote.FirebaseApi
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
abstract class RepairsModule {

    companion object {

        @Provides
        @Singleton
        fun ProvideFirebaseApi(
            firebaseAuth: FirebaseAuth,
            firebaseFirestore: FirebaseFirestore,
            firebaseStorage: FirebaseStorage
        ): FirebaseApi {
            return FirebaseApi(
                firebaseAuth = firebaseAuth,
                firebaseFirestore = firebaseFirestore,
                firebaseStorage = firebaseStorage
            )
        }

        @Provides
        @Singleton
        fun ProvideTiemedDatabase(app: Application): TiemedDatabase {
            return Room.databaseBuilder(
                app,
                TiemedDatabase::class.java,
                "tiemed_db"
            ).build()
        }

        @Provides
        @Singleton
        fun ProvideRepository(
            tiemedDatabase: TiemedDatabase,
            firebaseApi: FirebaseApi
        ): TiemedRepository {
            return TiemedRepositoryImplementation(
                tiemedDatabase.tiemedDao,
                firebaseApi
            )
        }
    }

    @Binds
    @Singleton
    abstract fun ProvideRepository(
        TiemedRepositoryImplementation: TiemedRepositoryImplementation
    ): TiemedRepositoryImplementation
}