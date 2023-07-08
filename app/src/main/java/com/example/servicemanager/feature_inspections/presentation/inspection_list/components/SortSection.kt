package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrpien.noteapp.feature_notes.domain.util.OrderMonotonicity
import com.adrpien.noteapp.feature_notes.domain.util.OrderType

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    orderType: OrderType = OrderType.Date(OrderMonotonicity.Descending),
    onOrderChange: (OrderType) -> Unit,
    onToggleMonotonicity: (OrderType) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,

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

            }
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                OrderMonotonicityButton(
                    isAscending = orderType.orderMonotonicity is OrderMonotonicity.Ascending,
                    onClick = { onToggleMonotonicity(orderType.toggleOrderMonotonicity()) }
                )
            }
        }

    }
}





