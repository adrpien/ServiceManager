package com.example.servicemanager.feature_inspections_domain.util

sealed class InspectionOrderType(val orderMonotonicity: InspectionOrderMonotonicity) {
    class Hospital(orderMonotonicity: InspectionOrderMonotonicity): InspectionOrderType(orderMonotonicity)
    class State(orderMonotonicity: InspectionOrderMonotonicity): InspectionOrderType(orderMonotonicity)
    class Date(orderMonotonicity: InspectionOrderMonotonicity): InspectionOrderType(orderMonotonicity)


    fun changeOrderMonotonicityType(orderMonotonicity: InspectionOrderMonotonicity) : InspectionOrderType {
        return when(this) {
            is Hospital -> Hospital(orderMonotonicity)
            is Date -> Date(orderMonotonicity)
            is State -> State(orderMonotonicity)
        }
    }

    fun toggleOrderMonotonicity() : InspectionOrderType {
        return when(this.orderMonotonicity) {
            is InspectionOrderMonotonicity.Descending -> {
                when (this) {
                    is Hospital -> Hospital(InspectionOrderMonotonicity.Ascending)
                    is Date -> Date(InspectionOrderMonotonicity.Ascending)
                    is State -> State(InspectionOrderMonotonicity.Ascending)
                }
            }
            is InspectionOrderMonotonicity.Ascending -> {
                when (this) {
                    is Hospital -> Hospital(InspectionOrderMonotonicity.Descending)
                    is Date -> Date(InspectionOrderMonotonicity.Descending)
                    is State -> State(InspectionOrderMonotonicity.Descending)
                }
            }

        }
    }

}
