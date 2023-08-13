package com.example.servicemanager.core.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicemanager.core.compose.DefaultTextFieldState
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    state: MutableState<DefaultTextFieldState>,
) {
    Column() {
        TextField(
            value = state.value.value,
            label = {
                Text(text = state.value.hint)
            },
            onValueChange = onValueChanged,
            enabled = state.value.clickable,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(2.dp, TiemedLightBlue),
            colors = TextFieldDefaults.textFieldColors(
                textColor = TiemedLightBlue,
                backgroundColor = TiemedVeryLightBeige,
                unfocusedLabelColor = TiemedLightBlue,
                focusedLabelColor = TiemedLightBlue,
                disabledLabelColor = TiemedLightBlue,
                errorLabelColor = TiemedLightBlue
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}