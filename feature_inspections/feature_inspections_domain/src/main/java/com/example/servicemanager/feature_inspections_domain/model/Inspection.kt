package com.example.servicemanager.feature_inspections_domain.model


import java.util.*

data class Inspection(
    val inspectionId: String = "0",
    val hospitalId: String = "",
    val ward: String = "",
    val comment: String = "",
    val inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    val technicianId: String = "",
    val recipient: String = "",
    val inspectionStateId: String = "",
    val estStateId: String = "",
    val deviceName: String = "",
    val deviceManufacturer: String = "",
    val deviceModel: String = "",
    val deviceSn: String = "",
    val deviceIn: String = ""
    ) {

}