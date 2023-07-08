package com.adrpien.noteapp.feature_notes.domain.util

sealed class OrderType(val orderMonotonicity: OrderMonotonicity) {
    class Hospital(orderMonotonicity: OrderMonotonicity): OrderType(orderMonotonicity)
    class State(orderMonotonicity: OrderMonotonicity): OrderType(orderMonotonicity)
    class Date(orderMonotonicity: OrderMonotonicity): OrderType(orderMonotonicity)


    fun changeOrderMonotonicityType(orderMonotonicity: OrderMonotonicity) : OrderType {
        return when(this) {
            is OrderType.Hospital -> OrderType.Hospital(orderMonotonicity)
            is OrderType.Date -> OrderType.Date(orderMonotonicity)
            is OrderType.State -> OrderType.State(orderMonotonicity)
        }
    }

    fun toggleOrderMonotonicity() : OrderType {
        return when(this.orderMonotonicity) {
            is OrderMonotonicity.Descending -> {
                when (this) {
                    is OrderType.Hospital -> OrderType.Hospital(OrderMonotonicity.Ascending)
                    is OrderType.Date -> OrderType.Date(OrderMonotonicity.Ascending)
                    is OrderType.State -> OrderType.State(OrderMonotonicity.Ascending)
                }
            }
            is OrderMonotonicity.Ascending -> {
                when (this) {
                    is OrderType.Hospital -> OrderType.Hospital(OrderMonotonicity.Descending)
                    is OrderType.Date -> OrderType.Date(OrderMonotonicity.Descending)
                    is OrderType.State -> OrderType.State(OrderMonotonicity.Descending)
                }
            }

        }
    }

}
