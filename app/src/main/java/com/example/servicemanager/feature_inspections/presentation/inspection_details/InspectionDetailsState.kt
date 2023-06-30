package com.example.servicemanager.feature_inspections.presentation.inspection_details

import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_inspections.domain.model.Inspection

data class InspectionDetailsState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val inspection: Inspection = Inspection(),
    val hospitalList: List<Hospital> = emptyList(),
    val inspectionStateList: List<InspectionState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val deviceList: List<Device> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val inspectionList: List<Inspection> = emptyList(),
    ) {

}
