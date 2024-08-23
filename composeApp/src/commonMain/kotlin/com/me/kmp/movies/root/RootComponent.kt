package com.me.kmp.movies.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.backhandler.BackHandlerOwner

interface RootComponent : BackHandlerOwner {

    val stack: Value<ChildStack<*, Child>>

    val backDispatcher: BackDispatcher

    override val backHandler: BackHandler
        get() = backDispatcher

    fun onBackEvent()

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Detail(val component: DetailComponent) : Child()
    }
}
