package com.example.caching_data.repository

import com.example.caching_data.R
import com.example.caching_data.local.CachingDatabaseDao
import com.example.caching_domain.repository.CachingRepository
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.servicemanager.feature_inspections_data.mappers.toInspectionEntity
import com.example.servicemanager.feature_inspections_data.remote.InspectionFirebaseApi
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_repairs_data.mappers.toRepairEntity
import com.example.servicemanager.feature_repairs_data.remote.RepairFirebaseApi
import com.example.servicemanager.feature_repairs_domain.model.Repair

class CachingRepositoryImplementation(
    private val cachingDatabaseDao: CachingDatabaseDao,
    private val inspectionFirebaseApi: InspectionFirebaseApi,
    private val repairFirebaseApi: RepairFirebaseApi
): CachingRepository {
    override suspend fun cacheInspection(inspection: Inspection){
        cachingDatabaseDao.cacheInspection(inspection.toInspectionEntity())

    }

    override suspend fun cacheRepair(repair: Repair) {
        cachingDatabaseDao.cacheRepair(repair.toRepairEntity())
    }

    override suspend fun syncInspections(inspections: List<Inspection>): Resource<String> {
        var isSuccessful = true
        inspections.forEach { inspection ->
            val result = inspectionFirebaseApi.createInspection(inspection)
            if (result.resourceState != ResourceState.SUCCESS) isSuccessful = false
        }
        return if(isSuccessful) {
            Resource(
                ResourceState.SUCCESS,
                null,
                UiText.StringResource(R.string.inspections_successfully_synced)
            )
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.inspections_sync_error)
            )
        }
    }

    override suspend fun syncRepairs(repairs: List<Repair>): Resource<String> {
        var isSuccessful = true

        repairs.forEach { repair: Repair ->
            repairFirebaseApi.createRepair(repair)
        }
        return if(isSuccessful) {
            Resource(
                ResourceState.SUCCESS,
                null,
                UiText.StringResource(R.string.inspections_successfully_synced)
            )
        } else {
            Resource(
                ResourceState.ERROR,
                null,
                UiText.StringResource(R.string.inspections_sync_error)
            )
        }
    }

    override suspend fun getCachedInspections(): List<Inspection> {
        return cachingDatabaseDao.getCachedInspections().map { it.toInspection() }
    }

    override suspend fun getCachedRepairs(): List<Repair> {
        return cachingDatabaseDao.getCachedRepairs().map { it.toRepair() }
    }
}