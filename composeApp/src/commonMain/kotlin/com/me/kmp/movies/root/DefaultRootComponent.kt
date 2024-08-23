package com.me.kmp.movies.root

import androidx.lifecycle.ViewModelStoreOwner
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.ui.screens.detail.viewModelStoreOwner

import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, BackHandlerOwner {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Home,
        ) { config, ctx ->
            when (config) {
                is Config.Home -> RootComponent.Child.Home(homeComponent(ctx))
                is Config.DetailMovie -> RootComponent.Child.Detail(
                    detailComponent(ctx.viewModelStoreOwner(), config.movie.id)
                )
            }
        }

    override val backDispatcher: BackDispatcher
        get() = BackDispatcher()

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        HomeComponentImpl(
            componentContext = componentContext,
            navigateToDetail = { movie ->
                navigation.push(Config.DetailMovie(movie))
            }
        )

    private fun detailComponent(
        viewModelStoreOwner: ViewModelStoreOwner,
        movieId: Int
    ): DetailComponent =
        DetailComponentImpl(
            viewModelStoreOwner = viewModelStoreOwner,
            onFinished = navigation::pop,
            movieId = movieId
        )

    override fun onBackEvent() {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config
        data class DetailMovie(val movie: MovieModel) : Config
    }

    override val backHandler: BackHandler
        get() = componentContext.backHandler
}
