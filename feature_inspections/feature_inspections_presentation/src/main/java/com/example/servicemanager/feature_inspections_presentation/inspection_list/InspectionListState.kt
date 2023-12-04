package com.example.servicemanager.feature_inspections_presentation.inspection_list

import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderMonotonicity
import com.example.servicemanager.feature_inspections_domain.util.InspectionOrderType
import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.InspectionState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.User
import com.example.servicemanager.feature_app_domain.model.UserType
import com.example.servicemanager.feature_inspections_domain.model.Inspection

data class InspectionListState(
    val user: User = User(),
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
    val inspectionOrderType: InspectionOrderType = InspectionOrderType.State(
        InspectionOrderMonotonicity.Ascending),
    val hospital: Hospital? = null,
    val userTypeList: List<UserType> = emptyList()
) {

}
