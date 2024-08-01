package com.me.kmp.movies.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.kmp.movies.data.repository.database.dao.MoviesDao
import com.me.kmp.movies.domain.model.MovieModel

const val DATABASE_NAME = "movies.db"

// Review this interface due to there is a bug in the code and we need make this fake interface
// to avoid that this bug is detected by the compiler.
// In coming versions of the plugin this bug will be fixed and we can delete this interface.
interface DB {
    fun clearAllTables()
}

@Database(
    entities = [
        MovieModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase(), DB {
    abstract fun moviesDao(): MoviesDao
    override fun clearAllTables(){}
}