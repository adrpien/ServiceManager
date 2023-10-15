package com.example.core_ui.components.other

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> DefaultSelectionSection(
    modifier: Modifier = Modifier,
    itemList: List<T>,
    selectedItem: T,
    onItemChanged: (T) -> Unit,
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
            itemList.forEach { item ->
                DefaultRadioButton(
                    title = item.toString(),
                    selected = item.toString() == selectedItem.toString(),
                    onClick = { onItemChanged(item) },
                    isClickable = enabled
                )
            }
        }
    }
}
