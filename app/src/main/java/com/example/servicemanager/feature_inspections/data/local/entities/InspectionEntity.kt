package com.example.servicemanager.feature_inspections.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import java.util.*

@Entity
data class InspectionEntity(
    @PrimaryKey(autoGenerate = false)
    val inspectionId: String,
    val hospitalId: String = "",
    val ward: String = "",
    val comment: String = "",
    val inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    val recipient: String = "",
    val recipientSignature: String = "",
    val technicianId: String = "",
    val inspectionStateId: String = "",
    val estStateId: String = "",
    val deviceName: String = "",
    val deviceManufacturer: String = "",
    val deviceModel: String = "",
    val deviceSn: String = "",
    val deviceIn: String = "",
    ) {

    fun toInspection(): Inspection {
        return Inspection(
            inspectionId = inspectionId,
            hospitalId = hospitalId,
            ward = ward,
            comment = comment,
            inspectionDate = inspectionDate,
            technicianId = technicianId,
            recipient = recipient,
            recipientSignature = recipientSignature,
            inspectionStateId = inspectionStateId,
            estStateId = estStateId,
            deviceName = deviceName,
            deviceManufacturer = deviceManufacturer,
            deviceModel = deviceModel,
            deviceSn = deviceSn,
            deviceIn = deviceIn
        )
    }
}
