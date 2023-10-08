package com.example.feature_app_presentation.components.est_states

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature_app_presentation.components.hospital_selection.SelectRadioButton
import com.example.servicemanager.feature_app_domain.model.EstState

@Composable
fun EstStateSelectionSection(
    modifier: Modifier = Modifier,
    estStateList: List<EstState>,
    estState: EstState,
    onEstStateChange: (EstState) -> Unit,
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
            estStateList.forEach { item ->
                SelectRadioButton(
                    title = item.estState,
                    selected = item.estStateId == estState.estStateId,
                    onClick = { onEstStateChange(item) },
                    isClickable = enabled
                )
            }
        }
    }
}





