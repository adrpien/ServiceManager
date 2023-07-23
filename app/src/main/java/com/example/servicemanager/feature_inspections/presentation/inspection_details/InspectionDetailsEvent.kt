package com.example.servicemanager.feature_inspections.presentation.inspection_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_inspections.domain.model.Inspection

sealed class InspectionDetailsEvent {
    object SaveInspection: InspectionDetailsEvent()
    object SaveSignature: InspectionDetailsEvent()
    object UpdateInspection: InspectionDetailsEvent()
    object UpdateSignature: InspectionDetailsEvent()
    data class UpdateState(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateSignatureState(val signature: Bitmap): InspectionDetailsEvent()

}