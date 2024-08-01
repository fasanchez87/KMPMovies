package com.me.kmp.movies.domain.model

data class MoviesModel(
    val page: Int,
    val movies: List<MovieModel>,
    val totalPages: Int,
    val totalResults: Int,
)
