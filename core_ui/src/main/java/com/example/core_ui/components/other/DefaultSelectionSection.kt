package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> DefaultSelectionSection(
    modifier: Modifier = Modifier,
    itemList: List<T>,
    nameList: List<String>,
    selectedItem: T,
    onItemChanged: (T) -> Unit,
    enabled: Boolean
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(
                width = 1.dp,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
            ),
                MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
            .horizontalScroll(scrollState),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            itemList.forEachIndexed() { index, item ->
                DefaultRadioButton(
                    title = nameList[index],
                    selected = item.toString() == selectedItem.toString(),
                    onClick = { onItemChanged(item) },
                    isClickable = enabled
                )
            }
        }
    }
}
