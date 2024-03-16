package com.example.servicemanager.feature_repairs_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.servicemanager.feature_repairs_data.local.entities.RepairEntity


@Database (
    entities = [
        RepairEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class RepairDatabase : RoomDatabase() {


    abstract val repairDatabaseDao: RepairDatabaseDao

    companion object {
        const val REPAIR_DATABASE_NAME = "repair_database"
    }

}