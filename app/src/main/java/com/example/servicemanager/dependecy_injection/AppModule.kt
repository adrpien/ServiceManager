package com.example.servicemanager.dependecy_injection

import android.app.Application
import com.example.logger_data.logger.FirebaseLogger
import com.example.logger_domain.logger.AppLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideFireBaseAnalytics(app: Application): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideAppLogger(firebaseAnalytics: FirebaseAnalytics): AppLogger {
        return FirebaseLogger(firebaseAnalytics = firebaseAnalytics)
    }

}
