@file:JvmName("ExitAlertDialogKt")

package com.example.core_ui.components.alert_dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.core_ui.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState

@Composable
fun ExitAlertDialog(
    exitAlertDialogState: MaterialDialogState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    MaterialDialog(
        dialogState = exitAlertDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        buttons = {
            positiveButton(
                text = stringResource(R.string.confirm),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
            ) {
                onConfirm()
            }
            negativeButton(
                text = stringResource(R.string.cancel),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
            )
            {
                onDismiss()
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))
        {
            Text(
                text = stringResource(R.string.do_you_want_save_changes),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}