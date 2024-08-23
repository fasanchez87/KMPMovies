package com.me.kmp.movies

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.me.kmp.movies.di.initKoin
import com.me.kmp.movies.root.RootComponent

@OptIn(ExperimentalDecomposeApi::class)
fun MainViewController(root: RootComponent) =
    ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        PredictiveBackGestureOverlay(
            backDispatcher = root.backDispatcher,
            backIcon = { progress, _ ->
//                PredictiveBackGestureIcon(
//                    imageVector = Icons.Default.ArrowBack,
//                    progress = progress,
//                )
            },
            modifier = Modifier.fillMaxSize(),
            onClose = root::onBackEvent,
        ) {
            App(root = root)
        }
    }
