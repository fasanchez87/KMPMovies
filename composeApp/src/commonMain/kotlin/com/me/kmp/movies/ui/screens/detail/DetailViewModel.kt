package com.me.kmp.movies.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.utils.ResultObject
import com.me.kmp.movies.utils.launchFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel(
    id: Int
) : ViewModel(), KoinComponent {

    private val repository: MoviesRepository by inject()

    private val _movie = MutableStateFlow<ResultObject<MovieModel>>(ResultObject.Loading())
    val movie: StateFlow<ResultObject<MovieModel>> = _movie.asStateFlow()

    init {
        getMovieById(id)
    }

    private fun getMovieById(id: Int) = launchFlow(stateFlow = _movie) {
        repository.fetchMovieById(id)
    }

    fun setFavorite(movieModel: MovieModel) = launchFlow(stateFlow = _movie) {
        repository.setFavoriteMovie(movieModel)
    }
}
