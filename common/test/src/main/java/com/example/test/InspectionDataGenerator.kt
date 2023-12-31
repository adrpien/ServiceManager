package com.example.test

import com.example.servicemanager.feature_inspections_domain.model.Inspection
import java.util.Calendar

fun inspection(
    inspectionId: String,
    hospitalId: String = "",
    ward: String = "",
    comment: String = "",
    inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    technicianId: String = "",
    recipient: String = "",
    inspectionStateId: String = "",
    estStateId: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "",
    deviceIn: String = ""
): Inspection {
    return Inspection(
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
        deviceSn = "000",
        deviceIn = "000",
        )
}

fun inspectionPassed(
    inspectionId: String,
    hospitalId: String = "",
    ward: String = "",
    comment: String = "",
    inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    technicianId: String = "",
    recipient: String = "",
    estStateId: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "000",
    deviceIn: String = "000"
): Inspection {
    return Inspection(
        inspectionId = inspectionId,
        hospitalId = hospitalId,
        ward = ward,
        comment = comment,
        inspectionDate = inspectionDate,
        technicianId = technicianId,
        recipient = recipient,
        inspectionStateId = "Ib02K8iK0ppGBXzLIWhr",
        estStateId = estStateId,
        deviceName = deviceName,
        deviceManufacturer = deviceManufacturer,
        deviceModel = deviceModel,
        deviceSn = deviceSn,
        deviceIn = deviceIn,
    )
}

fun inspectionFailed(
    inspectionId: String,
    hospitalId: String = "",
    ward: String = "",
    comment: String = "",
    inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    technicianId: String = "",
    recipient: String = "",
    estStateId: String = "",
    deviceName: String = "",
    deviceManufacturer: String = "",
    deviceModel: String = "",
    deviceSn: String = "000",
    deviceIn: String = "000"
): Inspection {
    return Inspection(
        inspectionId = inspectionId,
        hospitalId = hospitalId,
        ward = ward,
        comment = comment,
        inspectionDate = inspectionDate,
        technicianId = technicianId,
        recipient = recipient,
        inspectionStateId = "xNLrZaWcb6WxRbHTRUAE",
        estStateId = estStateId,
        deviceName = deviceName,
        deviceManufacturer = deviceManufacturer,
        deviceModel = deviceModel,
        deviceSn = deviceSn,
        deviceIn = deviceIn,
    )
}

