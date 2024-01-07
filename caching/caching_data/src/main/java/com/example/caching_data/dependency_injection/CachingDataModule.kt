package com.example.caching_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.caching_data.local.CachingDatabase
import com.example.caching_data.repository.CachingRepositoryImplementation
import com.example.caching_domain.repository.CachingRepository
import com.example.servicemanager.feature_app_data.remote.AppFirebaseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CachingDataModule {

    @Provides
    @Singleton
    fun provideInspectionDatabase(app: Application): CachingDatabase {
        return Room.databaseBuilder(
            app,
            CachingDatabase::class.java,
            "caching_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCachingRepository(
        appFirebaseApi: AppFirebaseApi,
        cachingDatabase:CachingDatabase
    ): CachingRepository {
        return CachingRepositoryImplementation(
            cachingDatabaseDao = cachingDatabase.cachingDatabaseDao,
            appFirebaseApi = appFirebaseApi,
        )
    }
}