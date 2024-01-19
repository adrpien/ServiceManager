package com.example.servicemanager.feature_inspections_domain.util

sealed class InspectionOrderMonotonicity {
    object Descending: InspectionOrderMonotonicity()
    object Ascending: InspectionOrderMonotonicity()
}
