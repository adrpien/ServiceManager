package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.core.theme.VeryLightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    state: MutableState<DefaultTextFieldState>,
) {
    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                // .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary), MaterialTheme.shapes.medium),
            value = state.value.value,
            label = {
                Row(modifier = Modifier.border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = if(state.value.clickable) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                        ),
                    shape = MaterialTheme.shapes.extraSmall
                )
                    .padding(start = 8.dp, end = 8.dp)
                    .background(
                        Color.Transparent
                    )
                ) {
                    Text(
                        text = state.value.hint,
                        modifier = Modifier.background(Color.Transparent)
                    )
                }

            },
            onValueChange = onValueChanged,
            enabled = state.value.clickable,
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                disabledLabelColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                disabledBorderColor = MaterialTheme.colorScheme.onSecondary,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun DefaultTextFieldPreview() {

    val state = remember {
        mutableStateOf(DefaultTextFieldState())
    }

    Column {
        DefaultTextField(
            onValueChanged = { },
            state = state
        )
        DefaultTextField(
            onValueChanged = { },
            state = state
        )
    }


}