package com.me.kmp.movies.data.repository.network.api

import com.me.kmp.movies.data.entity.MoviesEntity

interface HomeApi {
    suspend fun getMovies(): List<MoviesEntity>
}
