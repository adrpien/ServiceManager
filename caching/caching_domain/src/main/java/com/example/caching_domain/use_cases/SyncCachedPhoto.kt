package com.example.caching_domain.use_cases

import com.example.caching_domain.R
import com.example.caching_domain.model.Photo
import com.example.caching_domain.repository.CachingRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.servicemanager.feature_app_domain.repository.AppRepository
import javax.inject.Inject

class SyncCachedPhoto @Inject constructor (
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(photo: Photo, byteArray: ByteArray): Resource<String> {
        return appRepository.createSignature(photo.photoId, byteArray)
    }
}