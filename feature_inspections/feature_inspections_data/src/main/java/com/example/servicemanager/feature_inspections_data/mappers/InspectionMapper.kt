package com.example.servicemanager.feature_inspections_data.mappers


import com.example.servicemanager.feature_inspections_data.local.entities.InspectionEntity
import com.example.servicemanager.feature_inspections_data.remote.dto.InspectionDto
import com.example.servicemanager.feature_inspections_domain.model.Inspection

fun Inspection.toInspectionEntity(): InspectionEntity {
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
        deviceSn = deviceSn,
        deviceIn = deviceIn,
        signatureId = signatureId
    )
}

fun Inspection.toInspectionDto(): InspectionDto {
    return InspectionDto(
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
        deviceSn = deviceSn,
        deviceIn = deviceIn,
    )
}
