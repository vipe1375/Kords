/*
 * Kords
 * Copyright (C) 2024 Victor Pezennec--Deutsch
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    scrim = Grey300
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
    scrim = Grey700
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