package com.me.kmp.movies.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.me.kmp.movies.data.repository.database.DATABASE_NAME
import com.me.kmp.movies.data.repository.database.MoviesDatabase
import com.me.kmp.movies.data.repository.database.instantiateImpl
import platform.Foundation.NSHomeDirectory

fun getDataBaseBuilder(): RoomDatabase.Builder<MoviesDatabase> {
    val dbFile = NSHomeDirectory() + "/$DATABASE_NAME"
    return Room.databaseBuilder<MoviesDatabase>(
        name = dbFile,
        factory = {
            MoviesDatabase::class.instantiateImpl()
        }
    )
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
}
