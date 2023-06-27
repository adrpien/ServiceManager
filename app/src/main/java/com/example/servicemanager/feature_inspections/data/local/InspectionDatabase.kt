package com.example.servicemanager.feature_inspections.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity


@Database (
    entities = [
        InspectionEntity::class,
    ],
    version = 1
)
abstract class InspectionDatabase() : RoomDatabase() {


    abstract val inspectionDatabaseDao: InspectionDatabaseDao

    companion object {
        const val INSPECTION_DATABASE_NAME = "inspection_database"
    }

}