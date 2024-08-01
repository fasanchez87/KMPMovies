package com.me.kmp.movies.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesEntity(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val movies: List<MovieEntity>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)
