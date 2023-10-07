package com.example.servicemanager.feature_app_data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.servicemanager.feature_app_data.local.entities.EstStateEntity
import com.example.servicemanager.feature_app_data.local.entities.HospitalEntity
import com.example.servicemanager.feature_app_data.local.entities.InspectionStateEntity
import com.example.servicemanager.feature_app_data.local.entities.RepairStateEntity
import com.example.servicemanager.feature_app_data.local.entities.TechnicianEntity


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