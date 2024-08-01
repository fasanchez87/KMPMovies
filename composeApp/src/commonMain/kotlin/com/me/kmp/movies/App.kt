package com.me.kmp.movies

import androidx.compose.runtime.Composable
import com.me.kmp.movies.ui.screens.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        Navigation()
    }
}
