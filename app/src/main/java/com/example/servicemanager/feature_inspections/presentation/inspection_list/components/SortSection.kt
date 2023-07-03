package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adrpien.noteapp.feature_notes.domain.util.OrderMonotonicity
import com.adrpien.noteapp.feature_notes.domain.util.OrderType

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    orderType: OrderType = OrderType.Date(OrderMonotonicity.Descending),
    onOrderChange: (OrderType) -> Unit,
    onToggleMonotonicity: () -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SortRadioButton(
                title = "Hospital",
                selected = orderType is OrderType.Hospital,
                onClick = { onOrderChange(OrderType.Hospital(orderType.orderMonotonicity)) })
            SortRadioButton(
                title = "State",
                selected = orderType is OrderType.State,
                onClick = { onOrderChange(OrderType.State(orderType.orderMonotonicity)) })
            SortRadioButton(
                title = "Date",
                selected = orderType is OrderType.Date,
                onClick = { onOrderChange(OrderType.Date(orderType.orderMonotonicity)) })
            OrderMonotonicityButton(
                isAscending = false,
                onClick = { onToggleMonotonicity }
            )
        }
    }
}





