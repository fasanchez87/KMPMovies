package com.me.kmp.movies.di

import com.me.kmp.movies.data.database.getDataBaseBuilder
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDataBaseBuilder(get()) }
}
