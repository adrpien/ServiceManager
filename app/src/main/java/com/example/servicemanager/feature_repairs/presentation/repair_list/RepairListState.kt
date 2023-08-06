package com.example.servicemanager.feature_repairs.presentation.repair_list


import com.example.servicemanager.feature_app.domain.model.*
import com.example.servicemanager.feature_repairs.domain.model.Repair
import com.example.servicemanager.feature_repairs.domain.util.RepairOrderMonotonicity
import com.example.servicemanager.feature_repairs.domain.util.RepairOrderType
import com.google.firebase.firestore.auth.User
import javax.inject.Inject

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
