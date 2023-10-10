package com.example.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.core.values.Dimensions
import com.example.core.values.LocalSpacing

private val DarkColorPalette = darkColorScheme(
    primary = TiemedLightBeige,
    onPrimary = TiemedMediumBlue,
    secondary = TiemedLightTeal,
    onSecondary = TiemedDarkTeal,
    background = TiemedDarkGrey,
    onBackground = TiemedLightGrey,

)

private val LightColorPalette = lightColorScheme(
    primary = TiemedMediumBlue,
    onPrimary = TiemedLightBeige,
    secondary = TiemedDarkTeal,
    onSecondary = TiemedLightTeal,
    background = TiemedLightGrey,
    onBackground = TiemedDarkGrey
)

@Composable
fun ServiceManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    /*val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }*/

    val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkColorPalette else LightColorPalette
    }


    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )

    }
}
