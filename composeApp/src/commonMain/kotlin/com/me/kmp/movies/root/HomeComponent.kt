package com.me.kmp.movies.root

import com.me.kmp.movies.domain.model.MovieModel

interface HomeComponent {
    fun onMovieClick(movieModel: MovieModel)
}
