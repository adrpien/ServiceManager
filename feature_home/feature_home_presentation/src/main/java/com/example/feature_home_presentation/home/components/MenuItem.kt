package com.example.feature_home_presentation.home.components

import android.content.res.Resources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.theme.ServiceManagerTheme
import com.example.core.theme.Shapes
import com.example.core.util.UiText
import com.example.feature_home_presentation.R

@Composable
fun MenuItem(
    menuItemState: MenuItemState
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp)
                .border(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                    shape = MaterialTheme.shapes.medium
                )
        ,
            shape = Shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = menuItemState.onClick) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = menuItemState.icon,
                    contentDescription = menuItemState.text.asString(context)
                )
                Text(
                    text = menuItemState.text.asString(context),
                    modifier = Modifier
                )
            }
        }
    }
}

fun previewMenuItem(): MenuItemState {
    return MenuItemState(
            icon = Icons.Default.Logout,
            text = UiText.StringResource(id = R.string.log_out)
        ) { }
}
@Preview(showBackground = true)
@Composable
fun MenuItemPreview(){
    ServiceManagerTheme{
        MenuItem(menuItemState = previewMenuItem())
    }
}