package com.example.servicemanager.feature_inspections_domain.model


import java.util.*

/**
 * Inspection class represents one record of inspection order.
 *
 * inspectionId = 0  differ new record and old record
 * inspectionId = "" throws exception
 * inspectionDate is string of long millis value
 * default value of calendar is today
 * @listOfProperties is companion object used in ImportInspectionsFromFile use case  to
 * map excel cell to corresponding property of Inspection class.
 * Order of members must be the same in class constructor and listOfProperties.
 *
 */

data class Inspection(
    val inspectionId: String = "",
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
    val deviceIn: String = "",
    val signatureId: String = "0",
    ) {

    companion object {
        val listOfProperties: List<String> = listOf<String>(
            "inspectionId",
            "hospitalId",
            "ward",
            "comment",
            "inspectionDate",
            "technicianId",
            "recipient",
            "inspectionStateId",
            "estStateId",
            "deviceName",
            "deviceManufacturer",
            "deviceModel",
            "deviceSn",
            "deviceIn",
            "signatureId"
        )
    }

}