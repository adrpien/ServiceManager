package com.example.servicemanager.feature_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_app.domain.model.Device

@Entity
data class DeviceEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val deviceId: String,
    val name: String = "",
    val manufacturer: String = "",
    val model: String = "",
    val serialNumber: String = "",
    val inventoryNumber: String = "",
) {
    fun toDevice(): Device {
        return Device(
            deviceId = deviceId,
            name = name,
            manufacturer = manufacturer,
            model = model,
            serialNumber = serialNumber,
            inventoryNumber = inventoryNumber,
        )
    }
}
