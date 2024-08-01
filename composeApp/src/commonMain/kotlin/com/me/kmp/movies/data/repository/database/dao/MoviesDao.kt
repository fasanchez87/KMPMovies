package com.me.kmp.movies.data.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.domain.model.MoviesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieModel")
    fun fetchPopularMovies(): Flow<List<MovieModel>>

    @Query("SELECT * FROM MovieModel WHERE id = :id")
    fun fetchMovieById(id: Int): Flow<MovieModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieModel>)

    @Query("UPDATE MovieModel SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavoriteMovie(id: Int, isFavorite: Boolean)
}
