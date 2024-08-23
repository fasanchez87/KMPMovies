package com.me.kmp.movies

import androidx.compose.runtime.Composable
import com.me.kmp.movies.root.RootComponent
import com.me.kmp.movies.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App(root: RootComponent) {
    KoinContext {
        // Navigation()
        RootContent(component = root)
    }
}
