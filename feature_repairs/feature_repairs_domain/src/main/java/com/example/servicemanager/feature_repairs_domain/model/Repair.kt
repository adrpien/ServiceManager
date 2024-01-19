package com.example.servicemanager.feature_repairs_domain.model

import java.time.LocalDate
import java.util.Calendar

/**
 * Repair class represents one record of repair order.
 *
 * repairId = 0  differ new record and old record
 * repair = "" throws exception
 * repairingDate is string of long millis value
 * openingDate is string of long millis value
 * closingDate is string of long millis value
 * default value of calendar is today
 *
 */
data class Repair(
    var repairId: String = "",
    var repairStateId: String = "",
    var hospitalId: String = "",
    var ward: String = "",
    var defectDescription: String = "",
    var repairDescription: String = "",
    var partDescription: String = "",
    var comment: String = "",
    var estStateId: String = "",
    var closingDate: String = Calendar.getInstance().timeInMillis.toString(),
    var openingDate: String = Calendar.getInstance().timeInMillis.toString(),
    var repairingDate: String = Calendar.getInstance().timeInMillis.toString(),
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
    val signatureId: String = "0"
    )

