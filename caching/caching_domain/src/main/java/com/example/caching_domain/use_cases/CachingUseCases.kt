package com.example.caching_domain.use_cases

data class CachingUseCases(
    val cacheInspection: CacheInspection,
    val syncData: SyncData,
    val cacheRepair: CacheRepair
)
