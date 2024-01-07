package com.example.caching_data.mappers

import com.example.caching_data.local.entities.PhotoEntity
import com.example.caching_domain.model.Photo

fun Photo.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        photoId = photoId,
        photoDescription = photoDescription,
        uri = uri
    )
}