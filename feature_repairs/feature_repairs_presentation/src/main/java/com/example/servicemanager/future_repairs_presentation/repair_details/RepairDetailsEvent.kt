package com.example.servicemanager.future_repairs_presentation.repair_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_repairs_domain.model.Repair

sealed class RepairDetailsEvent {
    data class SaveRepair(val repair: Repair): RepairDetailsEvent()
    data class UpdateRepair(val repair: Repair): RepairDetailsEvent()
    data class UpdateRepairState(val repair: Repair): RepairDetailsEvent()
    data class UpdateSignatureState(val signature: Bitmap): RepairDetailsEvent()
    data class SetIsInEditMode(val value: Boolean): RepairDetailsEvent()


}