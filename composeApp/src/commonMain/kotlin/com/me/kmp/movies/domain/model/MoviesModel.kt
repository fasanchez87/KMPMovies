package com.me.kmp.movies.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.me.kmp.movies.data.repository.database.MovieModelListConverter

@Entity
@TypeConverters(MovieModelListConverter::class)
data class MoviesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val page: Int,
    val movies: List<MovieModel>,
    val totalPages: Int,
    val totalResults: Int,
)
