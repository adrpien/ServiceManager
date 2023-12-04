package com.example.servicemanager.feature_app_data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.servicemanager.feature_app_data.local.TypeConverterList
import com.example.servicemanager.feature_app_domain.model.UserType

@TypeConverters(TypeConverterList::class)
@Entity
data class UserTypeEntity (
    @PrimaryKey(autoGenerate = false)
    val userTypeId: String,
    val userTypeName: String = "",
    val hospitals: List<String> = emptyList()
){

    fun toUserType(): UserType {
        return UserType(
            userTypeId =  userTypeId,
            userTypeName = userTypeName,
            hospitals = hospitals
        )
    }
}

