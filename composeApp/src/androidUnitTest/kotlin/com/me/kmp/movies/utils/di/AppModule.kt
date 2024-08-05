package com.me.kmp.movies.utils.di

import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.ui.screens.home.HomeViewModel
import com.me.kmp.movies.utils.repository.MoviesRepositoryFake
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val testModule = module {
    viewModel { HomeViewModel(get()) }
    single<MoviesRepository> {
        MoviesRepositoryFake(
            get(),
            get()
        )
    }
}
