package com.example.servicemanager.dependecy_injection

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.servicemanager.feature_user.data.local.UserDatabase
import com.example.servicemanager.feature_user.data.local.UserDatabaseDao
import com.example.servicemanager.feature_user.data.remote.UserFirebaseApi
import com.example.servicemanager.feature_user.data.repository.UserRepositoryImplementation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserFirebaseApi(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): UserFirebaseApi {
        return UserFirebaseApi(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userFirebaseApi: UserFirebaseApi,
        userDatabaseDatabase: UserDatabase
    ): UserRepositoryImplementation {
        return UserRepositoryImplementation(
            userFirebaseApi = userFirebaseApi,
            userDatabaseDao = userDatabaseDatabase.userDatabaseDao
        )
    }
}