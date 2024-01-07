package com.example.caching_data.repository

import com.example.caching_data.local.CachingDatabaseDao
import com.example.caching_data.mappers.toPhotoEntity
import com.example.caching_domain.model.Photo
import com.example.caching_domain.repository.CachingRepository
import com.example.core.util.Resource
import com.example.servicemanager.feature_app_data.remote.AppFirebaseApi

class CachingRepositoryImplementation(
    private val cachingDatabaseDao: CachingDatabaseDao,
    private val appFirebaseApi: AppFirebaseApi
): CachingRepository {
    override suspend fun cachePhoto(photo: Photo) {
        cachingDatabaseDao.cachePhoto(photo.toPhotoEntity())
    }

    override suspend fun syncCachedPhoto(photo: Photo, byteArray: ByteArray): Resource<String> {
            return appFirebaseApi.uploadSignature(
                signatureBytes = byteArray,
                signatureId = photo.photoId
            )

    }

    override suspend fun getCachedPhotos(): List<Photo> {
        return cachingDatabaseDao.getCachedPhotos().map { it.toPhoto() }
    }

    override suspend fun deleteCachedPhoto(photoId: String) {
        return cachingDatabaseDao.cleanCachedPhoto(photoId)
    }


}