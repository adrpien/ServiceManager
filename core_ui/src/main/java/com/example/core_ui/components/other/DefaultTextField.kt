package com.example.core_ui.components.other

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedLightBeige
import com.example.core.theme.TiemedVeryLightBlue

@OptIn(ExperimentalMaterial3Api::class)
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
                focusedTextColor = TiemedLightBlue,
                containerColor = TiemedLightBeige,
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