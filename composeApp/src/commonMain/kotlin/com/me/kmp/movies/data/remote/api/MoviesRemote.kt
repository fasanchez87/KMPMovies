package com.me.kmp.movies.data.remote.api

import com.me.kmp.movies.data.entity.MovieEntity
import com.me.kmp.movies.data.entity.MoviesEntity
import com.me.kmp.movies.data.mapper.MovieMapper
import com.me.kmp.movies.data.mapper.MoviesMapper
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.domain.model.MoviesModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MoviesRemote(
    private val client: HttpClient,
    private val mapperMovies: MoviesMapper,
    private val mapperMovie: MovieMapper,
) {
    suspend fun fetchPopularMovies(region: String): MoviesModel =
        client.get(ENDPOINT_POPULAR_MOVIES) {
            parameter("sort_by", SORT_BY)
            parameter("region", region)
        }.body<MoviesEntity>().let {
            mapperMovies.toModel(it)
        }

    suspend fun fetchMovieById(id: Int): MovieModel =
        client.get("$ENDPOINT_DETAIL_MOVIE/$id")
            .body<MovieEntity>().let {
                mapperMovie.toModel(it)
            }

    companion object {
        private const val ENDPOINT_POPULAR_MOVIES = "3/discover/movie"
        private const val ENDPOINT_DETAIL_MOVIE = "/3/movie"
        private const val SORT_BY = "popularity.desc"
    }
}
