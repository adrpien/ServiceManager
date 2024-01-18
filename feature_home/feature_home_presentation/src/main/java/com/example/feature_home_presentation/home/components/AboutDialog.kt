@file:JvmName("ExitAlertDialogKt")

package com.example.feature_home_presentation.home.components

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
fun AboutDialog(
    aboutAlertDialogState: MaterialDialogState,
) {
    MaterialDialog(
        dialogState = aboutAlertDialogState,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        backgroundColor = MaterialTheme.colorScheme.primary,
        buttons = {
            positiveButton(
                text = stringResource(R.string.confirm),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary)
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))
        {
            Text(
                text = stringResource(com.example.feature_home_presentation.R.string.made_by_adi),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}