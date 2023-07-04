package com.example.servicemanager.feature_inspections.presentation.inspection_list

import com.adrpien.noteapp.feature_notes.domain.util.OrderType

sealed class InspectionListEvent {

    object Refresh: InspectionListEvent()

    data class onSearchQueryChange(val searchQuery: String): InspectionListEvent()

    data class orderInspectionList(val orderType: OrderType): InspectionListEvent()
    object  ToggleSortSectionVisibility : InspectionListEvent()

    object  ToggleOrderMonotonicity : InspectionListEvent()


}