package com.example.servicemanager.feature_inspections.presentation.inspection_list

import com.adrpien.noteapp.feature_notes.domain.util.OrderMonotonicity
import com.adrpien.noteapp.feature_notes.domain.util.OrderType
import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_inspections.domain.model.Inspection

data class InspectionListState(
    val inspectionList: List<Inspection> = emptyList(),
    val hospitalList: List<Hospital> = emptyList(),
    val inspectionStateList: List<InspectionState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val isSortSectionVisible: Boolean = false,
    val isHospitalFilterSectionVisible: Boolean = false,
    val orderType: OrderType = OrderType.State(OrderMonotonicity.Ascending),
    val hospital: Hospital = Hospital()
) {

}
