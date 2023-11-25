package com.example.core.dependency_injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.core.preferences.AppPreferencesImplementation
import com.example.core.preferences.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun providePreferences(
        app: Application,
    ): AppPreferences {
        return AppPreferencesImplementation(
            app.applicationContext
        )
    }
}