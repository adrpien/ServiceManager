package com.example.logger

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
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

