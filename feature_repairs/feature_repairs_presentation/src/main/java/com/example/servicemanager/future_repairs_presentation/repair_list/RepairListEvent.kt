package com.example.servicemanager.future_repairs_presentation.repair_list

import com.example.servicemanager.feature_app_domain.model.Hospital
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType

sealed class RepairListEvent {

    object Refresh: RepairListEvent()
    data class OnSearchQueryChange(val searchQuery: String): RepairListEvent()
    data class OrderRepairList(val repairOrderType: RepairOrderType): RepairListEvent()
    data class FilterRepairListByHospital(val hospital: Hospital): RepairListEvent()
    object  ToggleSortSectionVisibility : RepairListEvent()
    object  ToggleHospitalFilterSectionVisibility : RepairListEvent()
    data class  ToggleOrderMonotonicity(val repairOrderType: RepairOrderType) : RepairListEvent()
}