package com.example.servicemanager.feature_repairs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.servicemanager.feature_repairs.data.local.entities.RepairEntity


@Database (
    entities = [
        RepairEntity::class,
    ],
    version = 1
)
abstract class RepairDatabase() : RoomDatabase() {


    abstract val repairDatabaseDao: RepairDatabaseDao

    companion object {
        const val REPAIR_DATABASE_NAME = "repair_database"
    }

}