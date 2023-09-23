package com.example.servicemanager.feature_repairs_domain.model

import com.example.servicemanager.feature_repairs_data.local.entities.RepairEntity
import com.example.servicemanager.feature_repairs_data.remote.dto.RepairDto
import java.time.LocalDate


data class Repair(
    var repairId: String = "0",
    var repairStateId: String = "",
    var hospitalId: String = "",
    var ward: String = "",
    var defectDescription: String = "",
    var repairDescription: String = "",
    var partDescription: String = "",
    var comment: String = "",
    var estStateId: String = "",
    var closingDate: String = LocalDate.now().toEpochDay().toString(),
    var openingDate: String = LocalDate.now().toEpochDay().toString(),
    var repairingDate: String = LocalDate.now().toEpochDay().toString(),
    var pickupTechnicianId: String = "",
    var repairTechnicianId: String = "",
    var returnTechnicianId: String = "",
    var rate: String = "",
    var recipient: String = "",
    val deviceName: String = "",
    val deviceManufacturer: String = "",
    val deviceModel: String = "",
    val deviceSn: String = "",
    val deviceIn: String = "",
    ) {
    fun toRepairEntity(): RepairEntity {
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
            deviceIn = deviceIn
        )
    }

    fun toRepairDto(): RepairDto {
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
            deviceIn = deviceIn
        )
    }
}

