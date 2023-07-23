package com.example.servicemanager.feature_inspections.presentation.inspection_details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.servicemanager.core.components.DefaultTextFieldState
import com.example.servicemanager.ui.theme.TiemedMediumBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    state: MutableState<DefaultTextFieldState>,
) {
    Column() {
        TextField(
            value = state.value.text,
            label = {
                Text(text = state.value.hint)
            },
            onValueChange = onValueChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(2.dp, TiemedMediumBlue),
            colors = TextFieldDefaults.textFieldColors(
                textColor = TiemedMediumBlue,
                backgroundColor = TiemedVeryLightBeige,
                unfocusedLabelColor = TiemedMediumBlue,
                focusedLabelColor = TiemedMediumBlue,
                disabledLabelColor = TiemedMediumBlue,
                errorLabelColor = TiemedMediumBlue
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}