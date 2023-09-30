package com.example.servicemanager.future_repairs_presentation.repair_list

import com.example.servicemanager.feature_app.domain.model.Hospital
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType

sealed class RepairListEvent {

    object Refresh: RepairListEvent()
    data class onSearchQueryChange(val searchQuery: String): RepairListEvent()
    data class orderRepairList(val repairOrderType: RepairOrderType): RepairListEvent()
    data class filterRepairListByHospital(val hospital: Hospital): RepairListEvent()
    object  ToggleSortSectionVisibility : RepairListEvent()
    object  ToggleHospitalFilterSectionVisibility : RepairListEvent()
    data class  ToggleOrderMonotonicity(val repairOrderType: RepairOrderType) : RepairListEvent()
}