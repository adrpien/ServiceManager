package com.example.servicemanager.future_repairs_presentation.repair_list.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.other.DefaultRadioButton
import com.example.core_ui.components.other.OrderMonotonicityButton
import com.example.feature_repairs_presentation.R
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
                .padding(4.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary),
                    MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colorScheme.secondary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 4.dp)
                ,
                horizontalArrangement = Arrangement.Start,

            ) {
                DefaultRadioButton(
                    title = stringResource(R.string.hospital),
                    selected = repairOrderType is RepairOrderType.Hospital,
                    onClick = { onOrderChange(RepairOrderType.Hospital(repairOrderType.orderMonotonicity)) },
                    isClickable = true
                )
                DefaultRadioButton(
                    title = stringResource(R.string.state),
                    selected = repairOrderType is RepairOrderType.State,
                    onClick = { onOrderChange(RepairOrderType.State(repairOrderType.orderMonotonicity)) },
                    isClickable = true
                )
                DefaultRadioButton(
                    title = stringResource(R.string.date),
                    selected = repairOrderType is RepairOrderType.Date,
                    onClick = { onOrderChange(RepairOrderType.Date(repairOrderType.orderMonotonicity)) },
                    isClickable = true
                )
            }
            Row(
                modifier = Modifier.padding(end = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OrderMonotonicityButton(
                    isAscending = repairOrderType.orderMonotonicity is RepairOrderMonotonicity.Ascending,
                    onClick = { onToggleMonotonicity(repairOrderType.toggleOrderMonotonicity()) }
                )
            }
        }

    }
}





