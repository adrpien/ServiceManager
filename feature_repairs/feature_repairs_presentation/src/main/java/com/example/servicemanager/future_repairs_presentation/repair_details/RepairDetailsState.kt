package com.example.servicemanager.future_repairs_presentation.repair_details

import android.graphics.Bitmap
import com.example.core.theme.Dimensions.signatureHeight
import com.example.core.theme.Dimensions.signatureWidth
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_repairs_domain.model.Repair


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
