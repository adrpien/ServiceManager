package com.example.servicemanager.feature_inspections.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.servicemanager.feature_inspections.domain.model.Inspection
import java.util.*

@Entity
data class InspectionEntity(
    @PrimaryKey(autoGenerate = false)
    var inspectionId: String,
    var hospitalId: String = "",
    var ward: String = "",
    var comment: String = "",
    var inspectionDate: String = Calendar.getInstance().timeInMillis.toString(),
    var recipient: String = "",
    var recipientSignature: String = "",
    var technicianId: String = "",
    var inspectionStateId: String = "",
    var estStateId: String = "",
    var deviceName: String = "",
    var deviceManufacturer: String = "",
    var deviceModel: String = "",
    var deviceSN: String = "",
    var deviceIN: String = "",
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
            deviceSN = deviceSN,
            deviceIN = deviceIN
        )
    }
}
