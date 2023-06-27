package com.example.servicemanager.feature_app.data.remote.dto

import com.example.servicemanager.feature_app.data.local.entities.DeviceEntity

data class DeviceDto(
    val deviceId: String,
    val name: String = "",
    val manufacturer: String = "",
    val model: String = "",
    val serialNumber: String = "",
    val inventoryNumber: String = "",
){

    fun toDeviceEntity(): DeviceEntity {
        return DeviceEntity(
            deviceId = deviceId,
            name = name,
            manufacturer = manufacturer,
            model = model,
            serialNumber = serialNumber,
            inventoryNumber = inventoryNumber,
        )
    }

    fun toDeviceDto(): DeviceDto {
        return DeviceDto(
            deviceId = deviceId,
            name = name,
            manufacturer = manufacturer,
            model = model,
            serialNumber = serialNumber,
            inventoryNumber = inventoryNumber,
        )
    }
}