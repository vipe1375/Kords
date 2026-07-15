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

// Dark theme: cards (surface) are darker than the background, accents are blue.
private val BlueDarkColorScheme = darkColorScheme(
    primary = Blue200,
    onPrimary = White,
    primaryContainer = Blue300,
    onPrimaryContainer = White,
    inversePrimary = Blue200,

    secondary = Blue100,
    onSecondary = White,
    secondaryContainer = Blue100,   // inactive tonal buttons
    onSecondaryContainer = White,

    tertiary = Blue300,
    onTertiary = Blue100,
    tertiaryContainer = Blue300,
    onTertiaryContainer = White,

    background = Blue400,
    onBackground = White,

    surface = Blue500,              // cards
    onSurface = White,
    surfaceVariant = Blue300,
    onSurfaceVariant = Blue100,     // muted text
    surfaceContainerHigh = Blue500,
    surfaceContainerHighest = Blue500,

    outline = Grey300,
    scrim = Grey300
)

// Light theme: pale blue background, white cards, blue accents.
private val BlueLightColorScheme = lightColorScheme(
    primary = Blue200,
    onPrimary = White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue500,
    inversePrimary = Blue200,

    secondary = Blue200,
    onSecondary = White,
    secondaryContainer = Blue100,   // inactive tonal buttons (visible on white)
    onSecondaryContainer = Blue500,

    tertiary = Blue300,
    onTertiary = Blue300,           // used as muted text; see onSurfaceVariant note
    tertiaryContainer = Blue100,
    onTertiaryContainer = Blue500,

    background = Blue50,            // pale blue
    onBackground = Blue500,

    surface = White,               // cards
    onSurface = Blue500,
    surfaceVariant = Blue100,
    onSurfaceVariant = Blue300,     // muted text
    surfaceContainerHigh = White,
    surfaceContainerHighest = White,

    outline = Grey300,
    scrim = Grey700

)

@Composable
fun KordsJetpackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}