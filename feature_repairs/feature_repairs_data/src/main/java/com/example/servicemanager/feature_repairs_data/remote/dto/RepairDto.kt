package com.example.servicemanager.feature_repairs_data.remote.dto

import com.example.servicemanager.feature_repairs_data.local.entities.RepairEntity


data class RepairDto(
    val repairId: String = "0",
    val repairStateId: String = "",
    val deviceId: String = "",
    val hospitalId: String = "",
    val ward: String = "",
    val defectDescription: String = "",
    val repairDescription: String = "",
    val partDescription: String = "",
    val comment: String = "",
    val estTestId: String = "",
    val closingDate: String = "",
    val openingDate: String = "",
    val repairingDate: String = "",
    val pickupTechnicianId: String = "",
    val repairTechnicianId: String = "",
    val returnTechnicianId: String = "",
    val rate: String = "",
    val recipient: String = "",
    val recipientSignatureId: String = "",
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
        deviceId = deviceId,
        hospitalId = hospitalId,
        ward = ward,
        // photoList = photoList,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        // partList = partList,
        partDescription = partDescription,
        comment = comment,
        estTestId = estTestId,
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
            deviceId = deviceId,
            hospitalId = hospitalId,
            ward = ward,
            // photoList = photoList,
            defectDescription = defectDescription,
            repairDescription = repairDescription,
            // partList = partList,
            partDescription = partDescription,
            comment = comment,
            estTestId = estTestId,
            closingDate = closingDate,
            openingDate = openingDate,
            repairingDate = repairingDate,
            pickupTechnicianId = pickupTechnicianId,
            repairTechnicianId = repairTechnicianId,
            returnTechnicianId = returnTechnicianId,
            rate = rate,
            recipient = recipient,
            recipientSignatureId = recipientSignatureId,
            deviceName = deviceName,
            deviceManufacturer = deviceManufacturer,
            deviceModel = deviceModel,
            deviceSn = deviceSn,
            deviceIn = deviceIn
        )
    }
}

