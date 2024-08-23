package com.me.kmp.movies.root

import com.arkivanov.decompose.ComponentContext
import com.me.kmp.movies.domain.model.MovieModel

class HomeComponentImpl(
    componentContext: ComponentContext,
    private val navigateToDetail: (MovieModel) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    override fun onMovieClick(movieModel: MovieModel) {
        navigateToDetail(movieModel)
    }
}
