package com.example.caching_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.caching_data.local.entities.PhotoEntity

@Dao
interface CachingDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cachePhoto(photoEntity: PhotoEntity)

    @Query("SELECT * FROM photoentity")
    suspend fun getCachedPhotos(): List<PhotoEntity>

    @Query("DELETE FROM photoentity WHERE photoId LIKE :photoId")
    suspend fun cleanCachedPhoto(photoId: String)
}
