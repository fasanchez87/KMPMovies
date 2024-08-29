package com.me.kmp.movies.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.me.kmp.movies.data.repository.database.DATABASE_NAME
import com.me.kmp.movies.data.repository.database.MoviesDatabase
import com.me.kmp.movies.data.repository.database.instantiateImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask

//fun getDataBaseBuilder(): RoomDatabase.Builder<MoviesDatabase> {
//    val dbFile = NSHomeDirectory() + "/$DATABASE_NAME"
//    return Room.databaseBuilder<MoviesDatabase>(
//        name = dbFile,
//        factory = {
//            MoviesDatabase::class.instantiateImpl()
//        }
//    )
//        .fallbackToDestructiveMigration(true)
//        .setDriver(BundledSQLiteDriver())
//}

fun getDataBaseBuilder(): RoomDatabase.Builder<MoviesDatabase> {
    val dbFilePath = documentDirectory() + "/$DATABASE_NAME"
    return Room.databaseBuilder<MoviesDatabase>(
        name = dbFilePath,
        factory = {
            MoviesDatabase::class.instantiateImpl()
        }
    )
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
