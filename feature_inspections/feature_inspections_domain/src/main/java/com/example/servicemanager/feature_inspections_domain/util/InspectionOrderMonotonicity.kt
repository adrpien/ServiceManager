package com.example.servicemanager.feature_inspections.domain.util

sealed class InspectionOrderMonotonicity(){
    object Descending: InspectionOrderMonotonicity()
    object Ascending: InspectionOrderMonotonicity()
}
