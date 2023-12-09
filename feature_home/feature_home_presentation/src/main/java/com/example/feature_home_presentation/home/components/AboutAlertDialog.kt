package com.example.feature_home_presentation.home.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import com.example.core.util.UiText
import com.example.feature_home_presentation.R

@Composable
fun AboutAlertDialog(
    modifier: Modifier = Modifier,
    title: UiText,
    context: Context,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
        AlertDialog(
            shape = RectangleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            textContentColor = MaterialTheme.colorScheme.onPrimary,
            onDismissRequest = onDismissRequest,
            text = { Text(text = stringResource(R.string.made_by_adi)) },
            confirmButton = {
                Button(
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = { onConfirm() },
                ) {
                    Text(text = "Confirm")
                }
            },

        )
}
