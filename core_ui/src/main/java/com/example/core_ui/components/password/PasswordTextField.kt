package com.example.core_ui.components.password

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.components.textfield.DefaultTextFieldState

@Composable
fun PasswordTextField(
    state: MutableState<DefaultTextFieldState>,
    onValueChanged: (String) -> (Unit),
) {

    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            disabledTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            focusedBorderColor = if(state.value.clickable) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            unfocusedBorderColor = if(state.value.clickable) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            disabledBorderColor = if(state.value.clickable) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,

            ),
        shape = MaterialTheme.shapes.medium,
        value = state.value.value,
        onValueChange = onValueChanged,
        label = { Text( "Password") },
        singleLine = true,
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (passwordVisible.value) stringResource(R.string.hide_password) else stringResource(
                R.string.show_password
            )

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}){
                Icon(
                    imageVector  = image,
                    contentDescription = description,
                    tint = MaterialTheme.colorScheme.onSecondary)
            }
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}