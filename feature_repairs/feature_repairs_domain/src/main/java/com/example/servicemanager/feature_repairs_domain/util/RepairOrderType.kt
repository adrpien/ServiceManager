package com.example.servicemanager.feature_repairs_domain.util

sealed class RepairOrderType(val orderMonotonicity: RepairOrderMonotonicity) {
    class Hospital(orderMonotonicity: RepairOrderMonotonicity): RepairOrderType(orderMonotonicity)
    class State(orderMonotonicity: RepairOrderMonotonicity): RepairOrderType(orderMonotonicity)
    class Date(orderMonotonicity: RepairOrderMonotonicity): RepairOrderType(orderMonotonicity)


    fun changeOrderMonotonicityType(orderMonotonicity: RepairOrderMonotonicity) : RepairOrderType {
        return when(this) {
            is Hospital -> Hospital(orderMonotonicity)
            is Date -> Date(orderMonotonicity)
            is State -> State(orderMonotonicity)
        }
    }

    fun toggleOrderMonotonicity() : RepairOrderType {
        return when(this.orderMonotonicity) {
            is RepairOrderMonotonicity.Descending -> {
                when (this) {
                    is Hospital -> Hospital(RepairOrderMonotonicity.Ascending)
                    is Date -> Date(RepairOrderMonotonicity.Ascending)
                    is State -> State(RepairOrderMonotonicity.Ascending)
                }
            }
            is RepairOrderMonotonicity.Ascending -> {
                when (this) {
                    is Hospital -> Hospital(RepairOrderMonotonicity.Descending)
                    is Date -> Date(RepairOrderMonotonicity.Descending)
                    is State -> State(RepairOrderMonotonicity.Descending)
                }
            }

        }
    }

}
