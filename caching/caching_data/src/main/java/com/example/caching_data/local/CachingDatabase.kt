package com.example.caching_data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.servicemanager.feature_inspections_data.local.entities.InspectionEntity
import com.example.servicemanager.feature_repairs_data.local.entities.RepairEntity

@Database (
    entities = [
        InspectionEntity::class,
        RepairEntity::class
    ],
    version = 1
)
abstract class CachingDatabase(): RoomDatabase() {

    abstract val cachingDatabaseDao: CachingDatabaseDao

    companion object {
        const val CACHING_DATABASE_NAME = "caching_database"
    }
}
