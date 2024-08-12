package com.me.kmp.movies.data.repository

import com.me.kmp.movies.data.remote.api.MoviesRemote
import com.me.kmp.movies.data.repository.database.dao.MoviesDao
import com.me.kmp.movies.domain.model.MovieModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.onEach

class MoviesRepositoryImpl(
    private val remote: MoviesRemote,
    private val local: MoviesDao,
    private val region: RegionRepository
) : MoviesRepository {

    override val movies = local.fetchPopularMovies().onEach { movies ->
        if (movies.isEmpty()) {
            Napier.d("region from iOS: ${region.getRegion()}")
            remote
                .fetchPopularMovies(region.getRegion())
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
        .setFavoriteMovie(movie.copy(isFavorite = !movie.isFavorite))
        .let {
            local.fetchMovieById(movie.id)
        }

//    override suspend fun setFavoriteMovie2(movie: MovieModel) {
//        movie.let {
//            local.setFavoriteMovie(it.id, it.isFavorite)
//        }
//    }
}