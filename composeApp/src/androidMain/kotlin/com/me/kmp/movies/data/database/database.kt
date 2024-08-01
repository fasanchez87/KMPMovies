package com.me.kmp.movies.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.me.kmp.movies.data.repository.database.DATABASE_NAME
import com.me.kmp.movies.data.repository.database.MoviesDatabase

fun getDataBaseBuilder(context: Context): RoomDatabase.Builder<MoviesDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<MoviesDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    ).fallbackToDestructiveMigration(true)
}
