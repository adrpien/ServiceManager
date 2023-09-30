package com.example.servicemanager.feature_repairs.presentation.repair_details.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.core.compose.components.SelectRadioButton

@Composable
fun RepairStateSelectionSection(
    modifier: Modifier = Modifier,
    repairStateList: List<RepairState>,
    repairState: RepairState,
    onRepairStateChange: (RepairState) -> Unit,
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repairStateList.forEach { item ->
                SelectRadioButton(
                    title = item.repairState,
                    selected = item.repairStateId == repairState.repairStateId,
                    onClick = { onRepairStateChange(item) },
                isClickable = true)
            }
        }
    }
}





