package com.example.servicemanager.feature_authentication_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.servicemanager.feature_authentication.data.local.UserDatabase
import com.example.servicemanager.feature_authentication.data.remote.UserFirebaseApi
import com.example.servicemanager.feature_authentication.data.repository.UserRepositoryImplementation
import com.example.servicemanager.feature_authentication.domain.repository.UserRepository
import com.example.servicemanager.feature_authentication.domain.use_cases.Authenticate
import com.example.servicemanager.feature_authentication.domain.use_cases.GetCurrentUser
import com.example.servicemanager.feature_authentication.domain.use_cases.GetUser
import com.example.servicemanager.feature_authentication.domain.use_cases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationDataModule {

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
    ): UserRepository {
        return UserRepositoryImplementation(
            userFirebaseApi = userFirebaseApi,
            userDatabaseDao = userDatabaseDatabase.userDatabaseDao
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(
        repository: UserRepository
    ): UserUseCases {
        return UserUseCases(
            getUser = GetUser(repository),
            authenticate = Authenticate(repository),
            getCurrentUser = GetCurrentUser(repository)
        )
    }


}