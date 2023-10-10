package com.example.feature_app_presentation.components.other

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
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedLightBeige


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
        colors = TextFieldDefaults.colors(
            focusedTextColor = TiemedLightBlue,
            focusedContainerColor = TiemedLightBeige,
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