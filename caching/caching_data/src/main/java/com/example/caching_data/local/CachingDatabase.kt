package com.example.caching_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.caching_data.local.entities.PhotoEntity
import com.example.caching_domain.model.Photo

@Database (
    entities = [
        PhotoEntity::class,
    ],
    version = 1
)
abstract class CachingDatabase(): RoomDatabase() {

    abstract val cachingDatabaseDao: CachingDatabaseDao

    companion object {
        const val CACHING_DATABASE_NAME = "caching_database"
    }
}
