package com.example.servicemanager.feature_repairs.presentation.repair_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_inspections.presentation.inspection_details.InspectionDetailsEvent
import com.example.servicemanager.feature_repairs.domain.model.Repair

sealed class RepairDetailsEvent {
    data class SaveRepair(val repair: Repair): RepairDetailsEvent()
    data class UpdateRepair(val repair: Repair): RepairDetailsEvent()
    data class UpdateRepairState(val repair: Repair): RepairDetailsEvent()
    data class UpdateSignatureState(val signature: Bitmap): RepairDetailsEvent()
    data class SetIsInEditMode(val value: Boolean): RepairDetailsEvent()


}