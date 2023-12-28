package com.example.feature_home_presentation.import_inspections.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.DialogProperties
import com.example.core.util.UiText
import com.example.feature_home_presentation.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState

@Composable
fun ImportInspectionsAlertDialog(
    dialogState: MaterialDialogState,
    onClick: () -> Unit,
    content: String
) {
    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        buttons = {
            positiveButton(
                text = stringResource(com.example.core_ui.R.string.confirm),
                onClick = onClick,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )
            negativeButton(
                text = stringResource(id = R.string.cancel),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )

        }
    ) {
        Column {
            Text(
                text = content,
                color = MaterialTheme.colorScheme.onSecondary
                )
        }
    }
}