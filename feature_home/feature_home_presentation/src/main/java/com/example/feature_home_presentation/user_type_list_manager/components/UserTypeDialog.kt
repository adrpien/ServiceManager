package com.example.feature_home_presentation.user_type_list_manager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.core_ui.components.textfield.DefaultTextField
import com.example.core_ui.components.textfield.DefaultTextFieldState
import com.example.feature_home_presentation.R
import com.example.feature_home_presentation.technician_list_manager.TechnicianListManagerEvent
import com.example.servicemanager.feature_app_domain.model.Technician
import com.example.servicemanager.feature_app_domain.model.UserType
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.customView

@Composable
fun UserTypeDialog(
    userType: UserType,
    userTypeDialogState: MaterialDialogState,
    onConfirmClick: (UserType) -> Unit
) {
    val userTypeState = remember {
        mutableStateOf(userType)
    }

    val userTypeNameTextFieldState = remember { mutableStateOf(DefaultTextFieldState(
        hint = "UserType name",
        value = userType.userTypeName
    )) }

    MaterialDialog(
        dialogState = userTypeDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.medium,
        buttons = {
            positiveButton(
                text = stringResource(R.string.confirm),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                onClick = {
                    onConfirmClick(userTypeState.value)
                }
            )
            negativeButton(
                text = stringResource(R.string.cancel),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
            )
        }
    ) {
        customView {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    DefaultTextField(
                        onValueChanged = { string ->
                            userTypeNameTextFieldState.value = userTypeNameTextFieldState.value.copy(value = string)
                            userTypeState.value = userTypeState.value.copy( userTypeName =  string)
                        },
                        state = userTypeNameTextFieldState
                    )
                }
            }
        }
    }

}