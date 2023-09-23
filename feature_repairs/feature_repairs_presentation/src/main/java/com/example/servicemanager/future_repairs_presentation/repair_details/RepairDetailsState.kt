package com.example.servicemanager.future_repairs_presentation.repair_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.ui.theme.signatureHeight
import com.example.servicemanager.ui.theme.signatureWidth

data class RepairDetailsState(
    val isInEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val repair: Repair = Repair(),
    val hospitalList: List<Hospital> = emptyList(),
    val repairStateList: List<RepairState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val signature: Bitmap = Bitmap.createBitmap(signatureWidth, signatureHeight, Bitmap.Config.ARGB_8888)
    ) {

}
