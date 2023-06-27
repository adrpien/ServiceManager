package com.example.servicemanager.feature_inspections.data.local

import androidx.room.*
import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity

@Dao
interface InspectionDatabaseDao {

    /* ***** Inspections ************************************************************************ */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspectionEntity: InspectionEntity)

    @Transaction
    @Query("SELECT * FROM inspectionentity WHERE inspectionId LIKE :inspectionId")
    suspend fun getInspection(inspectionId: String): InspectionEntity

    @Transaction
    @Query("SELECT * FROM inspectionentity")
    suspend fun getInspectionList(): List<InspectionEntity>

    @Transaction
    @Query("DELETE FROM inspectionentity WHERE inspectionId LIKE :inspectionId")
    suspend fun deleteInspection(inspectionId: String)

    @Transaction
    @Query("DELETE FROM inspectionentity")
    suspend fun deleteAllInspections()

}