package com.example.servicemanager.feature_repairs_data.mappers

import com.example.servicemanager.feature_repairs_data.local.entities.RepairEntity
import com.example.servicemanager.feature_repairs_data.remote.dto.RepairDto
import com.example.servicemanager.feature_repairs_domain.model.Repair
import java.time.LocalDate
import kotlin.math.sin

fun Repair.toRepairEntity(): RepairEntity {
    return RepairEntity(
        repairId = repairId,
        repairStateId = repairStateId,
        hospitalId = hospitalId,
        ward = ward,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        partDescription = partDescription,
        comment = comment,
        estTestId = estStateId,
        closingDate = closingDate,
        openingDate = openingDate,
        repairingDate = repairingDate,
        pickupTechnicianId = pickupTechnicianId,
        repairTechnicianId = repairTechnicianId,
        returnTechnicianId = returnTechnicianId,
        rate = rate,
        recipient = recipient,
        deviceName = deviceName,
        deviceManufacturer = deviceManufacturer,
        deviceModel = deviceModel,
        deviceSn = deviceSn,
        deviceIn = deviceIn,
        signatureId = signatureId
    )
}

fun Repair.toRepairDto(): RepairDto {
    return RepairDto(
        repairId = repairId,
        repairStateId = repairStateId,
        hospitalId = hospitalId,
        ward = ward,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        partDescription = partDescription,
        comment = comment,
        estTestId = estStateId,
        closingDate = closingDate,
        openingDate = openingDate,
        repairingDate = repairingDate,
        pickupTechnicianId = pickupTechnicianId,
        repairTechnicianId = repairTechnicianId,
        returnTechnicianId = returnTechnicianId,
        rate = rate,
        recipient = recipient,
        deviceName = deviceName,
        deviceManufacturer = deviceManufacturer,
        deviceModel = deviceModel,
        deviceSn = deviceSn,
        deviceIn = deviceIn,
        signatureId = signatureId
    )
}


