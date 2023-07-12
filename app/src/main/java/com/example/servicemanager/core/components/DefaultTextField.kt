package com.example.servicemanager.feature_inspections.presentation.inspection_list.components

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
fun MutableState<DefaultTextFieldState>.InspectionTextField() {
    TextField(
        value = this.value.text,
        label = {
            Text(text = this.value.hint)
        },
        onValueChange = {
            this.value = this.value.copy(
                text = it
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    )
}