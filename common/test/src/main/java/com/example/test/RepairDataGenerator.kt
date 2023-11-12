package com.example.test

import com.example.servicemanager.feature_repairs_domain.model.Repair
import java.time.LocalDate

fun repair(
    repairId: String,
    repairStateId: String = "",
    hospitalId: String = "",
    ward: String = "",
    defectDescription: String = "",
    repairDescription: String = "",
    partDescription: String = "",
    comment: String = "",
    estStateId: String = "",
    closingDate: String = LocalDate.now().toEpochDay().toString(),
    openingDate: String = LocalDate.now().toEpochDay().toString(),
    repairingDate: String = LocalDate.now().toEpochDay().toString(),
    pickupTechnicianId: String = "",
    repairTechnicianId: String = "",
    returnTechnicianId: String = "",
    rate: String = "",
    recipient: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "",
    deviceIn: String = "",
) : Repair {
    return Repair(
        repairId = repairId,
        repairStateId = repairStateId,
        hospitalId = hospitalId,
        ward = ward,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        partDescription = partDescription,
        comment = comment,
        estStateId = estStateId,
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

fun repairRepaired(
    repairId: String,
    hospitalId: String = "",
    ward: String = "",
    defectDescription: String = "",
    repairDescription: String = "",
    partDescription: String = "",
    comment: String = "",
    estStateId: String = "",
    closingDate: String = LocalDate.now().toEpochDay().toString(),
    openingDate: String = LocalDate.now().toEpochDay().toString(),
    repairingDate: String = LocalDate.now().toEpochDay().toString(),
    pickupTechnicianId: String = "",
    repairTechnicianId: String = "",
    returnTechnicianId: String = "",
    rate: String = "",
    recipient: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "",
    deviceIn: String = "",
) : Repair {
    return Repair(
        repairId = repairId,
        repairStateId = "QWAM98irXyV1UAPbQedZ",
        hospitalId = hospitalId,
        ward = ward,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        partDescription = partDescription,
        comment = comment,
        estStateId = estStateId,
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

fun repairFinished(
    repairId: String,
    hospitalId: String = "",
    ward: String = "",
    defectDescription: String = "",
    repairDescription: String = "",
    partDescription: String = "",
    comment: String = "",
    estStateId: String = "",
    closingDate: String = LocalDate.now().toEpochDay().toString(),
    openingDate: String = LocalDate.now().toEpochDay().toString(),
    repairingDate: String = LocalDate.now().toEpochDay().toString(),
    pickupTechnicianId: String = "",
    repairTechnicianId: String = "",
    returnTechnicianId: String = "",
    rate: String = "",
    recipient: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "",
    deviceIn: String = "",
) : Repair {
    return Repair(
        repairId = repairId,
        repairStateId = "IuitUfbI2lFoxpTS28J5",
        hospitalId = hospitalId,
        ward = ward,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        partDescription = partDescription,
        comment = comment,
        estStateId = estStateId,
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

fun repairDiagnosed(
    repairId: String,
    hospitalId: String = "",
    ward: String = "",
    defectDescription: String = "",
    repairDescription: String = "",
    partDescription: String = "",
    comment: String = "",
    estStateId: String = "",
    closingDate: String = LocalDate.now().toEpochDay().toString(),
    openingDate: String = LocalDate.now().toEpochDay().toString(),
    repairingDate: String = LocalDate.now().toEpochDay().toString(),
    pickupTechnicianId: String = "",
    repairTechnicianId: String = "",
    returnTechnicianId: String = "",
    rate: String = "",
    recipient: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "",
    deviceIn: String = "",
) : Repair {
    return Repair(
        repairId = repairId,
        repairStateId = "YwfcQm726Sv9ofLiVqas",
        hospitalId = hospitalId,
        ward = ward,
        defectDescription = defectDescription,
        repairDescription = repairDescription,
        partDescription = partDescription,
        comment = comment,
        estStateId = estStateId,
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