package com.example.servicemanager.feature_repairs.data.local

import androidx.room.*
import com.example.servicemanager.feature_repairs.data.local.entities.RepairEntity


@Dao
interface RepairDatabaseDao {

    /* ***** Repairs **************************************************************************** */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepair(repairEntity: RepairEntity)

    @Transaction
    @Query("SELECT * FROM repairentity WHERE repairId LIKE :repairId")
    suspend fun getRepair(repairId: String): RepairEntity

    @Transaction
    @Query("SELECT * FROM repairentity")
    suspend fun getRepairList(): List<RepairEntity>

    @Transaction
    @Query("DELETE FROM repairentity WHERE repairId LIKE :repairId")
    suspend fun deleteRepair(repairId: String)

    @Transaction
    @Query("DELETE FROM repairentity")
    suspend fun deleteAllRepairs()

   }