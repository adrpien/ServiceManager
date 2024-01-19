package com.example.servicemanager.feature_app_data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.servicemanager.feature_app_data.local.room.entities.CachedSignatureEntity

import com.example.servicemanager.feature_app_data.local.room.entities.EstStateEntity
import com.example.servicemanager.feature_app_data.local.room.entities.HospitalEntity
import com.example.servicemanager.feature_app_data.local.room.entities.InspectionStateEntity
import com.example.servicemanager.feature_app_data.local.room.entities.RepairStateEntity
import com.example.servicemanager.feature_app_data.local.room.entities.TechnicianEntity
import com.example.servicemanager.feature_app_data.local.room.entities.UserTypeEntity


@Database (
    entities = [
        EstStateEntity::class,
        HospitalEntity::class,
        InspectionStateEntity::class,
        RepairStateEntity::class,
        TechnicianEntity::class,
        UserTypeEntity::class,
        CachedSignatureEntity::class
               ],
    version = 1
)
@TypeConverters(TypeConverterList::class)
abstract class AppDatabase : RoomDatabase() {


    abstract val appDatabaseDao: AppDatabaseDao

    companion object {
        const val APP_DATABASE_NAME = "app_database"
    }

}