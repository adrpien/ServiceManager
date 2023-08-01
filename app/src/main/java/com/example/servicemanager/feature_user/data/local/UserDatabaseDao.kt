package com.example.servicemanager.feature_user.data.local

import androidx.room.*
import com.example.servicemanager.feature_user.data.local.entities.UserEntity

@Dao
interface UserDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM userentity WHERE userId LIKE :userId")
    suspend fun getUser(userId: String): UserEntity

    @Transaction
    @Query ("DELETE FROM userentity WHERE userId LIKE :userId")
    suspend fun deleteUser(userId: String)

}