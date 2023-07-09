package com.example.servicemanager.feature_inspections.presentation.inspection_details

import com.example.servicemanager.feature_inspections.domain.model.Inspection

sealed class InspectionDetailsEvent {

    data class saveInspection(val inspection: Inspection): InspectionDetailsEvent()

    data class updateInspection(val inspection: Inspection): InspectionDetailsEvent()

}