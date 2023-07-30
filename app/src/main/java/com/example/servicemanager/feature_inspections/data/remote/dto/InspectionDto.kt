package com.example.servicemanager.feature_inspections.data.remote.dto

import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity
import java.util.*

data class InspectionDto(
    val inspectionId: String,
    val deviceId: String = "",
    val hospitalId: String = "",
    val ward: String = "",
    val comment: String = "",
    val inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    val technicianId: String = "",
    val recipient: String = "",
    val inspectionStateId: String = "",
    val estStateId: String = "",
    val deviceName: String = "",
    val deviceManufacturer: String = "",
    val deviceModel: String = "",
    val deviceSN: String = "",
    val deviceIN: String = "",
) {

    fun toInspectionEntity(): InspectionEntity {
        return InspectionEntity(
            inspectionId = inspectionId,
            hospitalId = hospitalId,
            ward = ward,
            comment = comment,
            inspectionDate = inspectionDate,
            technicianId = technicianId,
            recipient = recipient,
            inspectionStateId = inspectionStateId,
            estStateId = estStateId,
            deviceName = deviceName,
            deviceManufacturer = deviceManufacturer,
            deviceModel = deviceModel,
            deviceSn = deviceSN,
            deviceIn = deviceIN
        )
    }

    fun toInspectionDto(): InspectionDto {
        return InspectionDto(
            inspectionId = inspectionId,
            deviceId = deviceId,
            hospitalId = hospitalId,
            ward = ward,
            comment = comment,
            inspectionDate = inspectionDate,
            technicianId = technicianId,
            recipient = recipient,
            inspectionStateId = inspectionStateId,
            estStateId = estStateId,
            deviceName = deviceName,
            deviceManufacturer = deviceManufacturer,
            deviceModel = deviceModel,
            deviceSN = deviceSN,
            deviceIN = deviceIN

        )
    }
}
