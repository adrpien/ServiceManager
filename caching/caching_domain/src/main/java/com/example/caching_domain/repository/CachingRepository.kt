package com.example.caching_domain.repository

import com.example.caching_domain.model.Photo
import com.example.core.util.Resource

interface CachingRepository {
    suspend fun cachePhoto(photo: Photo)
    suspend fun syncCachedPhoto(photo: Photo, byteArray: ByteArray): Resource<String>
    suspend fun getCachedPhotos(): List<Photo>
    suspend fun deleteCachedPhoto(photoId: String)

}