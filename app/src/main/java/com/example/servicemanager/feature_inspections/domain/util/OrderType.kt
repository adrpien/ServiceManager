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
        return when(this) {
            is OrderType.Hospital -> {
                if (this.orderMonotonicity == OrderMonotonicity.Descending) {
                    OrderType.Hospital(OrderMonotonicity.Ascending)
                } else {
                    OrderType.Hospital(OrderMonotonicity.Ascending)
                }
            }
            is OrderType.Date -> {
                if (this.orderMonotonicity == OrderMonotonicity.Descending) {
                    OrderType.Date(OrderMonotonicity.Ascending)
                } else {
                    OrderType.Date(OrderMonotonicity.Ascending)
                }
            }
            is OrderType.State -> {
                if (this.orderMonotonicity == OrderMonotonicity.Descending) {
                    OrderType.State(OrderMonotonicity.Ascending)
                } else {
                    OrderType.State(OrderMonotonicity.Ascending)
                }
            }
        }
    }

}
