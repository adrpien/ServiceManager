package com.example.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.preferences.AppPreferences
import com.example.core.values.Dimensions
import com.example.core.values.LocalSpacing

private val DarkColorPalette = darkColorScheme(
    primary = MediumTeal,
    onPrimary = VeryLightTeal,
    secondary = MediumGrey,
    onSecondary = VeryLightGrey,
)

private val LightColorPalette = lightColorScheme(
    primary = VeryLightTeal,
    onPrimary = DarkTeal,
    secondary = VeryLightGrey,
    onSecondary = MediumGrey
)

@Composable
fun ServiceManagerTheme(
    preferences: AppPreferences = hiltViewModel(),
    content: @Composable () -> Unit,
) {

    val isInDarkMode = preferences.getIsDarkModeEnabled()
    // if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)  true
    val supportDynamicColorScheme = false
    val colorScheme = if (supportDynamicColorScheme) {
        val context = LocalContext.current
        if (isInDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (isInDarkMode) DarkColorPalette else LightColorPalette
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
