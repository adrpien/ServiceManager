package com.example.caching_domain.use_cases

import com.example.caching_domain.R
import com.example.caching_domain.repository.CachingRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import javax.inject.Inject

class SyncData @Inject constructor (
    private val repository: CachingRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        try {
            val inspectionList = repository.getCachedInspections().data
            val inspectionSyncResult = inspectionList?.let {
                repository.syncInspections(it)
            }
            val repairList = repository.getCachedRepairs().data
            val repairSyncResult = repairList?.let {
                repository.syncRepairs(repairList)
            }
            if(repairSyncResult?.resourceState == ResourceState.SUCCESS && inspectionSyncResult?.resourceState == ResourceState.SUCCESS) {
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.data_synced)
                )
            } else {
                return Resource(
                    ResourceState.ERROR,
                    null,
                    UiText.StringResource(R.string.data_sync_error)
                )
            }
        } catch (e: IllegalArgumentException) {
            return Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.unknown_error)
            )
        }
    }
}