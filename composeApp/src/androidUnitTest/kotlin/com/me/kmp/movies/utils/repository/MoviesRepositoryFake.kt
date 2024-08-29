package com.me.kmp.movies.utils.repository

import com.me.kmp.movies.data.remote.api.MoviesRemote
import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.data.repository.database.dao.MoviesDao
import com.me.kmp.movies.domain.model.MovieModel
import kotlinx.coroutines.flow.onEach

class MoviesRepositoryFake(
    private val remote: MoviesRemote,
    private val local: MoviesDao
) : MoviesRepository {

    override val movies = local.fetchPopularMovies().onEach { movies ->
        if (movies.isEmpty()) {
            remote
                .fetchPopularMovies("US")
                .movies
                .let {
                    local.insertMovies(it)
                }
        }
    }

    override suspend fun fetchPopularMovies(region: String) = remote
        .fetchPopularMovies(region)
        .movies

    override fun fetchMovieById(id: Int) = local.fetchMovieById(id).onEach { movie ->
        if (movie == null) {
            val remoteMovie = remote.fetchMovieById(id)
            local.insertMovies(listOf(remoteMovie))
        }
    }

    override suspend fun setFavoriteMovie(movie: MovieModel) = local
        .setFavoriteMovie(movie.id, movie.isFavorite)
        .let {
            local.fetchMovieById(movie.id)
        }

    override suspend fun setFavoriteMovie2(movie: MovieModel) {
        movie.let {
            local.setFavoriteMovie(it.id, it.isFavorite)
        }
    }
}
