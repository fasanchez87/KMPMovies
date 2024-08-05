package com.me.kmp.movies.di

import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.me.kmp.movies.data.database.getDataBaseBuilder
import com.me.kmp.movies.data.repository.RegionRepository
import com.me.kmp.movies.data.repository.RegionRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDataBaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factory<RegionRepository> {
        RegionRepositoryImpl(
            get(),
            get()
        )
    }
}
