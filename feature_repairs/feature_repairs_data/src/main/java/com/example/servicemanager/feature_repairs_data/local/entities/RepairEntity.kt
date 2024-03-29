package com.example.servicemanager.feature_repairs_data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_repairs_domain.model.Repair


@Entity
data class RepairEntity(
    @PrimaryKey(autoGenerate = false)
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
    val deviceName: String = "",
    val deviceManufacturer: String = "",
    val deviceModel: String = "",
    val deviceSn: String = "",
    val deviceIn: String = "",
    val signatureId: String = ""
) {

    fun toRepair(): Repair {
        return Repair(
            repairId = repairId,
            repairStateId = repairStateId,
            hospitalId = hospitalId,
            ward = ward,
            //photoList = photoList,
            defectDescription = defectDescription,
            repairDescription = repairDescription,
            // partList = partList,
            partDescription = partDescription,
            comment = comment,
            estStateId = estTestId,
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
}

