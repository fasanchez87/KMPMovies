package com.me.kmp.movies.data.repository.database

import androidx.room.TypeConverter
import com.me.kmp.movies.domain.model.MovieModel
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class MovieModelListConverter {
    @TypeConverter
    fun fromMovieModelList(
        movies: List<MovieModel>
    ): String = Json.encodeToString(ListSerializer(MovieModel.serializer()), movies)

    @TypeConverter
    fun toMovieModelList(
        moviesString: String
    ): List<MovieModel> = Json.decodeFromString(
        ListSerializer(MovieModel.serializer()),
        moviesString
    )
}
