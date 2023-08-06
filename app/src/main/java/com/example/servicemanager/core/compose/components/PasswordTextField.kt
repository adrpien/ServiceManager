package com.example.servicemanager.core.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.servicemanager.core.compose.DefaultTextFieldState
import com.example.servicemanager.ui.theme.TiemedLightBlue
import com.example.servicemanager.ui.theme.TiemedVeryLightBeige

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
            .border(2.dp, TiemedLightBlue),
        colors = TextFieldDefaults.textFieldColors(
            textColor = TiemedLightBlue,
            backgroundColor = TiemedVeryLightBeige,
            unfocusedLabelColor = TiemedLightBlue,
            focusedLabelColor = TiemedLightBlue,
            disabledLabelColor = TiemedLightBlue,
            errorLabelColor = TiemedLightBlue,
        ),
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