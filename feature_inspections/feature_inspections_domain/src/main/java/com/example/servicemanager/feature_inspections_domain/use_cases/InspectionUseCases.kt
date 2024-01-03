package com.example.servicemanager.feature_inspections_domain.use_cases

data class InspectionUseCases(
    val saveInspection: SaveInspection,
    val getInspection: GetInspection,
    val getInspectionList: GetInspectionList,
    val updateInspection: UpdateInspection,
    val cacheInspection: CacheInspection
)
