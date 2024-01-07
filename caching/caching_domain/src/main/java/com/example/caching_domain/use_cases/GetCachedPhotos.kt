package com.example.caching_domain.use_cases

import com.example.caching_domain.R.*
import com.example.caching_domain.model.Photo
import com.example.caching_domain.repository.CachingRepository
import javax.inject.Inject

class GetCachedPhotos @Inject constructor (
    private val repository: CachingRepository
) {
        suspend operator fun invoke(photo: Photo, byteArray: ByteArray): List<Photo> {
            return repository.getCachedPhotos()
        }
}