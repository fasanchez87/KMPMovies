package com.me.kmp.movies.ui.screens.home

import androidx.lifecycle.ViewModel
import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.utils.ResultObject
import com.me.kmp.movies.utils.launchFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    private val _status = MutableStateFlow<ResultObject<MoviesState>>(ResultObject.Loading())
    val status: StateFlow<ResultObject<MoviesState>> = _status.asStateFlow()

    private fun getMovies() = launchFlow(stateFlow = _status) {
        moviesRepository.movies.map {
            MoviesState(it)
        }
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMovies -> getMovies()
        }
    }
}

sealed class HomeIntent {
    data object LoadMovies : HomeIntent()
}

data class MoviesState(
    val movies: List<MovieModel>
)
