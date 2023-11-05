package com.example.servicemanager.feature_inspections_domain.use_cases

import com.example.servicemanager.feature_app_domain.model.Hospital
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

fun hospitalDluga(): Hospital {
    return Hospital(
        hospital = "Dluga",
        hospitalId = "ExF6PByjkDrHSCTzvYz3"
    )
}

fun hospitalORSK(): Hospital {
    return Hospital(
        hospital = "ORSK",
        hospitalId = "QtQqkoxoP24vakg5GcQu"
    )
}
