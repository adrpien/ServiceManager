package com.example.servicemanager.feature_inspections.domain.model

import com.example.servicemanager.feature_inspections.data.local.entities.InspectionEntity
import com.example.servicemanager.feature_inspections.data.remote.dto.InspectionDto
import java.util.*

data class Inspection(
    val inspectionId: String = "",
    val hospitalId: String = "",
    val ward: String = "",
    val comment: String = "",
    val inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    val technicianId: String = "",
    val recipient: String = "",
    val recipientSignature: String = "",
    val inspectionStateId: String = "",
    val estStateId: String = "",
    val deviceName: String = "",
    val deviceManufacturer: String = "",
    val deviceModel: String = "",
    val deviceSn: String = "",
    val deviceIn: String = "",
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
            deviceSN = deviceSn,
            deviceIN = deviceIn
        )
    }

    fun toInspectionDto(): InspectionDto {
        return InspectionDto(
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
            deviceSN = deviceSn,
            deviceIN = deviceIn
        )
    }
}