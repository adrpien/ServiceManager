package com.example.caching_domain.repository

import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.servicemanager.feature_repairs_domain.model.Repair

interface CachingRepository {
    suspend fun cacheInspection(inspection: Inspection)
    suspend fun cacheRepair(repair: Repair)
    suspend fun syncInspections(inspections: List<Inspection>): Resource<String>
    suspend fun syncRepairs(repairs: List<Repair>): Resource<String>
    suspend fun getCachedInspections(): List<Inspection>
    suspend fun getCachedRepairs(): List<Repair>

}