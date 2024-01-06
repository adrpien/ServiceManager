package com.example.caching_domain.use_cases

import com.example.caching_domain.R.*
import com.example.caching_domain.repository.CachingRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.servicemanager.feature_repairs_domain.model.Repair
import java.lang.IllegalArgumentException
import javax.inject.Inject

class CacheRepair @Inject constructor (
    private val repository: CachingRepository
) {
    class CacheRepair @Inject constructor (
        private val repository: CachingRepository
    ) {
        suspend operator fun invoke(repair: Repair): Resource<Repair> {
            try {
                return repository.cacheRepair(repair)
            } catch (e: IllegalArgumentException) {
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(string.unknown_error)
                )
            }

        }
    }
}