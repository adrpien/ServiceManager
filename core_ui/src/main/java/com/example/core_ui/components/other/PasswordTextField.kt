package com.example.core_ui.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige


@Composable
fun PasswordTextField(
    state: MutableState<DefaultTextFieldState>,
    onValueChanged: (String) -> (Unit),
) {

    var password = rememberSaveable { mutableStateOf("") }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.onSecondary, MaterialTheme.shapes.medium),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            disabledTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondary,
        ),
        shape = MaterialTheme.shapes.medium,
        value = state.value.value,
        onValueChange = onValueChanged,
        label = { Text("Password") },
        singleLine = true,
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector  = image, description)
            }
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}