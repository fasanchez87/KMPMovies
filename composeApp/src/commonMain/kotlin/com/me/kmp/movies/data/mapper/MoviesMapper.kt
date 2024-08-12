package com.me.kmp.movies.data.mapper

import com.me.kmp.movies.IMapper
import com.me.kmp.movies.data.entity.MoviesEntity
import com.me.kmp.movies.domain.model.MoviesModel

class MoviesMapper(
    private val movieMapper: MovieMapper,
) : IMapper<MoviesEntity, MoviesModel> {

    override fun toEntity(model: MoviesModel): MoviesEntity {
        return with(model) {
            MoviesEntity(
                page,
                movies.map { movieMapper.toEntity(it) },
                totalPages,
                totalResults,
            )
        }
    }

    override fun toModel(entity: MoviesEntity): MoviesModel {
        return with(entity) {
            MoviesModel(
                page = page,
                movies = movies.map { movieMapper.toModel(it) },
                totalPages = totalPages,
                totalResults = totalResults,
            )
        }
    }
}
