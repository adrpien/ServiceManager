package com.example.servicemanager.feature_authentication_data.dependency_injection

import com.example.servicemanager.feature_authentication_data.remote.UserFirebaseApi
import com.example.servicemanager.feature_authentication_data.repository.UserRepositoryImplementation
import com.example.servicemanager.feature_authentication_domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideUserFirebaseApi(
        firebaseAuth: FirebaseAuth,
    ): UserFirebaseApi {
        return UserFirebaseApi(
            firebaseAuth = firebaseAuth,
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userFirebaseApi: UserFirebaseApi,
    ): UserRepository {
        return UserRepositoryImplementation(
            userFirebaseApi = userFirebaseApi,
        )
    }

}