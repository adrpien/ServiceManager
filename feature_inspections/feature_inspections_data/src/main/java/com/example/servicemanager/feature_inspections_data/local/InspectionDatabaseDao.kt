package com.example.servicemanager.feature_inspections_data.local

import androidx.room.*
import com.example.servicemanager.feature_inspections_data.local.entities.InspectionEntity

@Dao
interface InspectionDatabaseDao {

    /* ***** Inspections ************************************************************************ */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspectionEntity: InspectionEntity)

    @Transaction
    @Query("SELECT * FROM inspectionentity WHERE inspectionId LIKE :inspectionId")
    suspend fun getInspection(inspectionId: String): InspectionEntity

    @Query("SELECT * FROM inspectionentity")
    suspend fun getInspectionList(): List<InspectionEntity>

    @Transaction
    @Query("DELETE FROM inspectionentity WHERE inspectionId LIKE :inspectionId")
    suspend fun deleteInspection(inspectionId: String)

    @Query("DELETE FROM inspectionentity")
    suspend fun deleteAllInspections()

}