package com.example.servicemanager.feature_inspections_presentation.inspection_details

import android.graphics.Bitmap
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_inspections_domain.model.Inspection

data class InspectionDetailsState(
    val isInEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val inspection: Inspection = Inspection(),
    val hospitalList: List<Hospital> = emptyList(),
    val inspectionStateList: List<InspectionState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val signature: Bitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888)
    ) {

}
