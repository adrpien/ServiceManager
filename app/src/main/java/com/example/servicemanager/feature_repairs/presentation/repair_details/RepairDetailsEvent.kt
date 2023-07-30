package com.example.servicemanager.feature_repairs.presentation.repair_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_repairs.domain.model.Repair

sealed class RepairDetailsEvent {
    object SaveRepair: RepairDetailsEvent()
    object UpdateRepair: RepairDetailsEvent()
    data class UpdateState(val repair: Repair): RepairDetailsEvent()
    data class UpdateSignatureState(val signature: Bitmap): RepairDetailsEvent()

}