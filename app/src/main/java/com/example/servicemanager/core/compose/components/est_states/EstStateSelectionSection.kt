package com.example.servicemanager.core.compose.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.feature_app.domain.model.EstState
import com.example.servicemanager.feature_app.domain.model.Hospital

@Composable
fun EstStateSelectionSection(
    modifier: Modifier = Modifier,
    estStateList: List<EstState>,
    estState: EstState,
    onEstStateChange: (EstState) -> Unit,
    isClickable: Boolean
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
                    isClickable = isClickable
                )
            }
        }
    }
}





