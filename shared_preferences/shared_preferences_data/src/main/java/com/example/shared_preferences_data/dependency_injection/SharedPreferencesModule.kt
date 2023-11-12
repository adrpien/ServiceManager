package com.example.shared_preferences_data.dependency_injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.shared_preferences_data.shared_preferences.DefaultPreferencesImplementation
import com.example.shared_preferences_domain.AppPreferences
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
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): AppPreferences {
        return DefaultPreferencesImplementation(
            sharedPreferences
        )
    }
}