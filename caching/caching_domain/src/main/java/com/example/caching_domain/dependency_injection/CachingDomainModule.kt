package com.example.caching_domain.dependency_injection

import android.app.Application
import com.example.caching_domain.repository.CachingRepository
import com.example.caching_domain.use_cases.CachePhoto
import com.example.caching_domain.use_cases.CachingUseCases
import com.example.caching_domain.use_cases.GetCachedPhotos
import com.example.caching_domain.use_cases.SavePhotoLocally
import com.example.caching_domain.use_cases.SyncCachedPhoto
import com.example.servicemanager.feature_app_domain.repository.AppRepository
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
        app: Application,
        cachingRepository: CachingRepository,
        appRepository: AppRepository
    ): CachingUseCases {
        return CachingUseCases(
            cachePhoto = CachePhoto(cachingRepository),
            syncCachedPhoto = SyncCachedPhoto(appRepository),
            savePhotoLocally = SavePhotoLocally(app.applicationContext),
            getCachedPhotos = GetCachedPhotos(cachingRepository)
        )
    }
}