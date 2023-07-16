package com.example.servicemanager.feature_inspections.presentation.inspection_details

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_inspections.domain.model.Inspection

data class InspectionDetailsState(
    val isInEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val inspection: Inspection = Inspection(),
    val hospitalList: List<Hospital> = emptyList(),
    val inspectionStateList: List<InspectionState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val signature: ImageBitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888).asImageBitmap()
    ) {

}
