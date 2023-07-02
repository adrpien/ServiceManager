package com.example.servicemanager.feature_inspections.presentation.inspection_list

sealed class InspectionListEvent {

    object Refresh: InspectionListEvent()
    data class onSearchQueryChange(val searchQuery: String): InspectionListEvent()
}