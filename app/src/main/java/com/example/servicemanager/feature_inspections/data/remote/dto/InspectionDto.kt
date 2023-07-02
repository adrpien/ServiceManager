package com.example.servicemanager.feature_inspections.data.remote.dto

import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity
import java.util.*

data class InspectionDto(
    val inspectionId: String,
    var deviceId: String = "",
    var hospitalId: String = "",
    var ward: String = "",
    var comment: String = "",
    var inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    var technicianId: String = "",
    var recipient: String = "",
    var recipientSignature: String = "",
    var inspectionStateId: String = "",
    var estStateId: String = "",
    var deviceName: String = "",
    var deviceManufacturer: String = "",
    var deviceModel: String = "",
    var deviceSN: String = "",
    var deviceIN: String = "",
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
            recipientSignature = recipientSignature,
            inspectionStateId = inspectionStateId,
            estStateId = estStateId,
            deviceName = deviceName,
            deviceManufacturer = deviceManufacturer,
            deviceModel = deviceModel,
            deviceSN = deviceSN,
            deviceIN = deviceIN
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
            recipientSignature = recipientSignature,
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
