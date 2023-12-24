package com.example.servicemanager.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.servicemanager.values.Dimensions
import com.example.servicemanager.values.LocalSpacing
import com.example.shared_preferences.AppPreferences
import com.example.shared_preferences.AppPreferencesImplementation

private val LocalMyColors = compositionLocalOf {
    DarkColorPalette
}
private val DarkColorPalette = darkColorScheme(
    primary = DarkTeal,
    onPrimary = VeryLightTeal,
    secondary = MediumTeal,
    onSecondary = LightTeal,
)

private val LightColorPalette = lightColorScheme(
    primary = VeryLightTeal,
    onPrimary = DarkTeal,
    secondary = LightTeal,
    onSecondary = MediumTeal
)

@Composable
fun ServiceManagerTheme(
    appPreferences: AppPreferences = AppPreferencesImplementation(
        LocalContext.current
    ),
    content: @Composable () -> Unit,
) {

    val context = LocalContext.current

    val isInDarkMode = appPreferences.getIsDarkModeEnabled()

    val supportDynamicColorScheme = Build.VERSION.SDK_INT < Build.VERSION_CODES.S

    val colorScheme = if (supportDynamicColorScheme) {
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
