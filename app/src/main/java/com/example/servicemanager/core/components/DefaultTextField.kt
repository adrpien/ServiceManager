package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.core.util.DefaultTextFieldState

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    state: MutableState<DefaultTextFieldState>,
) {
    TextField(
        value = state.value.text,
        label = {
            Text(text = state.value.hint)
        },
        onValueChange = onValueChanged,
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    )
}