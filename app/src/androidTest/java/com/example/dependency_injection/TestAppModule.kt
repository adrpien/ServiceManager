package com.example.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    @Named("inspection_test_db")
    fun provideInspectionDatabase(app: Application): InspectionDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            InspectionDatabase::class.java
        ).allowMainThreadQueries().build()
    }

}