package com.example.servicemanager.feature_inspections.presentation.inspection_details

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.servicemanager.feature_inspections.domain.model.Inspection

sealed class InspectionDetailsEvent {

    data class SaveInspection(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateInspection(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateState(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateSignature(val signature: ImageBitmap): InspectionDetailsEvent()


}