package com.example.caching_data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.caching_domain.model.Photo

@Entity
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    val photoId: String = "",
    val path: String = "",
    val photoDescription: String = ""
) {
    fun toPhoto(): Photo {
        return Photo(
            photoId = photoId,
            photoDescription = photoDescription,
            path = path
        )
    }
}
