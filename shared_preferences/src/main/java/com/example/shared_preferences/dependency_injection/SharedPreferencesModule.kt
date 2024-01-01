package com.example.shared_preferences.dependency_injection

import android.app.Application
import com.example.shared_preferences.AppPreferencesImplementation
import com.example.shared_preferences.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun providePreferences(
        app: Application,
    ): com.example.shared_preferences.AppPreferences {
        return com.example.shared_preferences.AppPreferencesImplementation(
            app.applicationContext
        )
    }
}