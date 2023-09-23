package com.example.servicemanager.feature_inspections.presentation.inspection_list

import com.example.servicemanager.feature_inspections.domain.util.InspectionOrderType
import com.example.servicemanager.feature_app.domain.model.Hospital

sealed class InspectionListEvent {

    object Refresh: InspectionListEvent()

    data class onSearchQueryChange(val searchQuery: String): InspectionListEvent()

    data class orderInspectionList(val inspectionOrderType: InspectionOrderType): InspectionListEvent()
    data class filterInspectionListByHospital(val hospital: Hospital): InspectionListEvent()
    object  ToggleSortSectionVisibility : InspectionListEvent()
    object  ToggleHospitalFilterSectionVisibility : InspectionListEvent()

    data class  ToggleOrderMonotonicity(val inspectionOrderType: InspectionOrderType) : InspectionListEvent()
}