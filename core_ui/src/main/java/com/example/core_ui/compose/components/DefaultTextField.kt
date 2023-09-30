package com.example.core_ui.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.TiemedLightBlue
import com.example.core.ui.theme.TiemedVeryLightBeige
import com.example.core.ui.theme.TiemedVeryLightBlue

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
                errorLabelColor = TiemedLightBlue,
                disabledTextColor = TiemedVeryLightBlue
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}