package com.example.servicemanager.future_repairs_presentation.repair_list


import com.example.servicemanager.feature_app_domain.model.EstState
import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_app_domain.model.RepairState
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_repairs_domain.model.Repair
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderMonotonicity
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType


data class RepairListState(
    val repairList: List<Repair> = emptyList(),
    val hospitalList: List<Hospital> = emptyList(),
    val repairStateList: List<RepairState> = emptyList(),
    val technicianList: List<Technician> = emptyList(),
    val estStateList: List<EstState> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val isSortSectionVisible: Boolean = false,
    val isHospitalFilterSectionVisible: Boolean = false,
    val repairOrderType: RepairOrderType = RepairOrderType.State(RepairOrderMonotonicity.Ascending),
    val hospital: Hospital? = null,
) {

}
