package com.me.kmp.movies.di

import com.me.kmp.movies.data.database.getDataBaseBuilder
import com.me.kmp.movies.data.repository.RegionRepository
import com.me.kmp.movies.data.repository.RegionRepositoryImpl
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDataBaseBuilder() }
    factory<RegionRepository> { RegionRepositoryImpl() }
}
