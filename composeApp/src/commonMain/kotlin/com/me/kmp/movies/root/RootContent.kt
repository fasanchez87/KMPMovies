package com.me.kmp.movies.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import com.me.kmp.movies.ui.screens.detail.DetailScreen
import com.me.kmp.movies.ui.screens.home.HomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(KoinExperimentalAPI::class)
@Composable
fun RootContent(
    component: RootComponent,
) {
    Children(
        stack = component.stack,
        modifier = Modifier.fillMaxSize(),
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackEvent,
        )
    ) {
        when (val instance = it.instance) {
            is RootComponent.Child.Home -> HomeScreen(component = instance.component)
            is RootComponent.Child.Detail -> CompositionLocalProvider(
                LocalViewModelStoreOwner provides instance.component.viewModelStoreOwner
            ) {
                DetailScreen(
                    // The Details screen now has its own ViewModelStore,
                    // Also, use assisted injection to pass the movieId to the ViewModel.
                    viewModel = koinViewModel(
                        parameters = { parametersOf(instance.component.movieId) }
                    ),
                    onBack = component::onBackEvent
                )
            }
        }
    }
}

expect fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T>
