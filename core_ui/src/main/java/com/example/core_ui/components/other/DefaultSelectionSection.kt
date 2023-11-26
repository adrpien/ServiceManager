package com.example.core_ui.components.other

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.util.MapperExtensionFunction.toMap
import kotlin.reflect.KProperty

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
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .horizontalScroll(scrollState),
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
