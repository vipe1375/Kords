package com.vipedev.kords.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BlueDarkColorScheme = darkColorScheme(
    primary = Blue200,
    onPrimary = White,
    primaryContainer = Blue200,
    onPrimaryContainer = White,
    inversePrimary = Blue200,

    secondary = Blue100,
    onSecondary = White,
    secondaryContainer = Blue100,
    onSecondaryContainer = White,

    tertiary = Blue300,
    onTertiary = Blue100,
    tertiaryContainer = Blue300,
    onTertiaryContainer = White,

    background = Blue400,
    onBackground = White,
    surface = Blue500,
    //surfaceVariant = Blue500,
    /*
    onSurface: Color,

    onSurfaceVariant: Color,
    surfaceTint: Color,
    inverseSurface: Color,
    inverseOnSurface: Color,
    error: Color,
    onError: Color,
    errorContainer: Color,
    onErrorContainer: Color,
    outline: Color,
    outlineVariant: Color,
    scrim: Color

             */
)

private val BlueLightColorScheme = lightColorScheme(
    primary = Blue200,
    onPrimary = Blue500,
    primaryContainer = Blue200,
    onPrimaryContainer = White,
    inversePrimary = Blue200,

    secondary = Blue100,
    onSecondary = White,
    secondaryContainer = Blue100,
    onSecondaryContainer = White,

    tertiary = Blue300,
    onTertiary = Blue300,
    tertiaryContainer = Blue300,
    onTertiaryContainer = White,

    background = Blue400,
    onBackground = Blue500,
    surface = White,
)

/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/


@Composable
fun KordsJetpackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*
    val colorScheme = when {
        /*
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> BlueDarkColorScheme
        else -> BlueLightColorScheme
    }*/
    val colorScheme = when {
        darkTheme -> BlueDarkColorScheme
        else -> BlueLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}