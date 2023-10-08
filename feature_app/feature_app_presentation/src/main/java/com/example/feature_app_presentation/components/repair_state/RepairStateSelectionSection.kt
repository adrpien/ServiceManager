package com.example.feature_app_presentation.components.repair_state

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_app_presentation.components.hospital_selection.SelectRadioButton
import com.example.servicemanager.feature_app_domain.model.RepairState

@Composable
fun RepairStateSelectionSection(
    modifier: Modifier = Modifier,
    repairStateList: List<RepairState>,
    repairState: RepairState,
    onRepairStateChange: (RepairState) -> Unit,
    enabled: Boolean
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
                isClickable = enabled)
            }
        }
    }
}





