package com.example.servicemanager.feature_inspections.presentation.inspection_list

import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_app.domain.use_cases.hospitals.GetHospitalList
import com.example.servicemanager.feature_app.domain.use_cases.technicians.GetTechnicianList
import com.example.servicemanager.feature_inspections.domain.model.Inspection

data class InspectionListState(
    val inspectionList: List<Inspection> = emptyList(),
    val hospitalList: List<Hospital> = emptyList(),
    val inspectionStateList: List<InspectionState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val deviceList: List<Device> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
) {

}
