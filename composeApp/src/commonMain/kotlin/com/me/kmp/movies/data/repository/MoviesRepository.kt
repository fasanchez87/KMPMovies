package com.me.kmp.movies.data.repository

import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.domain.model.MoviesModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    val movies: Flow<List<MovieModel>>

    suspend fun fetchPopularMovies(region: String = "US"): List<MovieModel>

    fun fetchMovieById(id: Int): Flow<MovieModel?>

    suspend fun setFavoriteMovie2(movie: MovieModel)

    suspend fun setFavoriteMovie(movie: MovieModel): Flow<MovieModel?>

}
