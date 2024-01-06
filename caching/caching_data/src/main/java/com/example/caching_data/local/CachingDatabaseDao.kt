package com.example.caching_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.servicemanager.feature_inspections_data.local.entities.InspectionEntity
import com.example.servicemanager.feature_repairs_data.local.entities.RepairEntity

@Dao
interface CachingDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheInspection(inspectionEntity: InspectionEntity)
    @Query( "SELECT * FROM inspectionentity")
    suspend fun getCachedInspections(): List<InspectionEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheRepair(repairEntity: RepairEntity)

    @Query("SELECT * FROM repairentity")
    suspend fun getCachedRepairs(): List<RepairEntity>
}