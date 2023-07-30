package com.example.servicemanager.feature_user.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.servicemanager.feature_user.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class UserDatabase(): RoomDatabase() {

    abstract val userDatabaseDao: UserDatabaseDao

    companion object {
        const val USER_DATABASE_NAME = "user_database"
    }
}