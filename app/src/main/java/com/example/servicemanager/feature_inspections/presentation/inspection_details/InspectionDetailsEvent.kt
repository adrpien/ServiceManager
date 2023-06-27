package com.example.servicemanager.feature_inspections.presentation.inspection_details

sealed class InspectionDetailsEvent {

    object Refresh: InspectionDetailsEvent()
    data class onSearchQueryChange(val query: String): InspectionDetailsEvent()
}