package com.example.caching_domain.dependency_injection

import com.example.caching_domain.repository.CachingRepository
import com.example.caching_domain.use_cases.CacheInspection
import com.example.caching_domain.use_cases.CacheRepair
import com.example.caching_domain.use_cases.CachingUseCases
import com.example.caching_domain.use_cases.SyncData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CachingDomainModule {

    @Provides
    @Singleton
    fun provideCachingUseCases(
        cachingRepository: CachingRepository
    ): CachingUseCases {
        return CachingUseCases(
            cacheInspection = CacheInspection(cachingRepository),
            cacheRepair = CacheRepair(cachingRepository),
            syncData = SyncData(cachingRepository)
        )
    }
}