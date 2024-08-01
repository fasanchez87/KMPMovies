package com.me.kmp.movies.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = colorScheme,
    ) {
        setSingletonImageLoaderFactory { context ->
            ImageLoader
                .Builder(context)
                .crossfade(true)
                .logger(DebugLogger())
                .build()
        }
        Surface(
            modifier = modifier,
            content = content,
        )
    }
}
