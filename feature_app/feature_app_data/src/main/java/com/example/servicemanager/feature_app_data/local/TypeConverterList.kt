package com.example.servicemanager.feature_app_data.local

import androidx.room.TypeConverter

class TypeConverterList {

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

}