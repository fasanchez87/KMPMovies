package com.me.kmp.movies

import androidx.compose.ui.window.ComposeUIViewController
import com.me.kmp.movies.di.initKoin

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        },
    ) {
        App()
    }
