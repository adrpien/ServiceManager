package com.example.caching_domain.use_cases

data class CachingUseCases(
    val cachePhoto: CachePhoto,
    val syncCachedPhoto: SyncCachedPhoto,
    val savePhotoLocally: SavePhotoLocally,
    val getCachedPhotos: GetCachedPhotos
    )
