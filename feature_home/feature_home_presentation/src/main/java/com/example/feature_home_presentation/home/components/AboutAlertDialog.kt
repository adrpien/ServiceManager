package com.example.feature_home_presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import com.example.core.theme.LightBlue
import com.example.core.theme.LightBeige
import com.example.core.theme.VeryLightBlue
import com.example.core.util.UiText

@Composable
fun AboutAlertDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onConfirm: () -> Unit,
) {
    val context = LocalContext.current

/*    if (isVisible){
        AlertDialog(
            shape = RectangleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            textContentColor = MaterialTheme.colorScheme.onPrimary,
            onDismissRequest = { isVisible = false },
            title = { Text(text = context.title) },
            text = { Text(text = contentText) },
            confirmButton = {
                Button(
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VeryLightBlue,
                        contentColor = LightBlue
                    ),
                    onClick = { onConfirm() },
                ) {
                    Text(text = "Confirm")
                }
            },

        )
    }*/

}