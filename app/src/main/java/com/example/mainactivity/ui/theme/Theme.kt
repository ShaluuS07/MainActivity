package com.example.mainactivity.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MatrimonyLightScheme = lightColorScheme(
    primary = MatrimonyPrimary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = MatrimonyPrimaryLight,
    onPrimaryContainer = MatrimonyPrimaryDark,
    secondary = MatrimonyAccent,
    onSecondary = Color(0xFF1C1B1F),
    tertiary = MatrimonyPrimaryLight,
    background = MatrimonySurface,
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F),
    onBackground = Color(0xFF1C1B1F),
)

private val MatrimonyDarkScheme = darkColorScheme(
    primary = MatrimonyPrimaryLight,
    onPrimary = MatrimonyPrimaryDark,
    primaryContainer = MatrimonyPrimaryDark,
    secondary = MatrimonyAccent,
)

@Composable
fun MainActivityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MatrimonyDarkScheme
        else -> MatrimonyLightScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            window.statusBarColor = MatrimonyPrimaryDark.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
