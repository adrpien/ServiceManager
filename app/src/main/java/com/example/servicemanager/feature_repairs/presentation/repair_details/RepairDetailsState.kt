package com.example.servicemanager.feature_repairs.presentation.repair_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_repairs.domain.model.Repair

data class RepairDetailsState(
    val isInEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val repair: Repair = Repair(),
    val hospitalList: List<Hospital> = emptyList(),
    val repairStateList: List<RepairState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val signature: Bitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888)
    ) {

}
