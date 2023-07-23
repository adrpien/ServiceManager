package com.example.servicemanager.feature_repairs.domain.util

sealed class RepairOrderMonotonicity(){
    object Descending: RepairOrderMonotonicity()
    object Ascending: RepairOrderMonotonicity()
}
