package com.example.caching_data.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.caching_data.local.CachingDatabase
import com.example.caching_data.local.CachingDatabaseDao
import com.example.caching_data.repository.CachingRepositoryImplementation
import com.example.caching_domain.repository.CachingRepository
import com.example.servicemanager.feature_inspections_data.local.InspectionDatabase
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_repairs_data.remote.RepairFirebaseApi
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
    fun provideCachingDatabase(app: Application): CachingDatabase {
        return Room.databaseBuilder(
            app,
            CachingDatabase::class.java,
            "caching_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCachingRepository(
        repairFirebaseApi: RepairFirebaseApi,
        inspectionFirebaseApi: InspectionFirebaseApi,
        cachingDatabase: CachingDatabase
    ): CachingRepository {
        return CachingRepositoryImplementation(
            cachingDatabaseDao = cachingDatabase.cachingDatabaseDao,
            repairFirebaseApi = repairFirebaseApi,
            inspectionFirebaseApi = inspectionFirebaseApi,
        )
    }
}