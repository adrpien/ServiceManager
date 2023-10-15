package com.example.servicemanager.future_repairs_presentation.repair_list.components
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.other.DefaultRadioButton
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderMonotonicity
import com.example.servicemanager.feature_repairs_domain.util.RepairOrderType

@Composable
fun RepairSortSection(
    modifier: Modifier = Modifier,
    repairOrderType: RepairOrderType = RepairOrderType.Date(RepairOrderMonotonicity.Descending),
    onOrderChange: (RepairOrderType) -> Unit,
    onToggleMonotonicity: (RepairOrderType) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,

            ) {
                DefaultRadioButton(
                    title = "Hospital",
                    selected = repairOrderType is RepairOrderType.Hospital,
                    onClick = { onOrderChange(RepairOrderType.Hospital(repairOrderType.orderMonotonicity)) },
                    isClickable = true
                )
                DefaultRadioButton(
                    title = "State",
                    selected = repairOrderType is RepairOrderType.State,
                    onClick = { onOrderChange(RepairOrderType.State(repairOrderType.orderMonotonicity)) },
                    isClickable = true
                )
                DefaultRadioButton(
                    title = "Date",
                    selected = repairOrderType is RepairOrderType.Date,
                    onClick = { onOrderChange(RepairOrderType.Date(repairOrderType.orderMonotonicity)) },
                    isClickable = true
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.example.core_ui.components.other.OrderMonotonicityButton(
                    isAscending = repairOrderType.orderMonotonicity is RepairOrderMonotonicity.Ascending,
                    onClick = { onToggleMonotonicity(repairOrderType.toggleOrderMonotonicity()) }
                )
            }
        }

    }
}





