package com.me.kmp.movies.data.mapper

import com.me.kmp.movies.IMapper
import com.me.kmp.movies.data.entity.MovieEntity
import com.me.kmp.movies.domain.model.MovieModel

class MovieMapper : IMapper<MovieEntity, MovieModel> {
    override fun toModel(entity: MovieEntity): MovieModel {
        return with(entity) {
            MovieModel(
                id,
                adult,
                backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
                // genreIds?.map { GenreType.findById(it) },
                originalLanguage,
                originalTitle,
                overview,
                popularity,
                posterPath.let { "https://image.tmdb.org/t/p/w185/$it" },
                releaseDate,
                title,
                video,
                voteAverage,
                voteCount,
            )
        }
    }
}
