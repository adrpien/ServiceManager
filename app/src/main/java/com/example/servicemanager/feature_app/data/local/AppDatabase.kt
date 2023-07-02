package com.adrpien.tiemed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.servicemanager.feature_app.data.local.AppDatabaseDao
import com.example.servicemanager.feature_app.data.local.entities.*


@Database (
    entities = [
        EstStateEntity::class,
        HospitalEntity::class,
        InspectionStateEntity::class,
        RepairStateEntity::class,
        TechnicianEntity::class,
               ],
    version = 1
)
abstract class AppDatabase() : RoomDatabase() {


    abstract val appDatabaseDao: AppDatabaseDao

    companion object {
        const val APP_DATABASE_NAME = "app_database"
    }

}