package com.example.servicemanager.feature_inspections_presentation.inspection_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_inspections_domain.model.Inspection

sealed class InspectionDetailsEvent {
    data class SaveInspection(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateInspection(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateInspectionState(val inspection: Inspection): InspectionDetailsEvent()
    data class UpdateSignatureState(val signature: Bitmap): InspectionDetailsEvent()
    data class SetIsInEditMode(val value: Boolean): InspectionDetailsEvent()

}