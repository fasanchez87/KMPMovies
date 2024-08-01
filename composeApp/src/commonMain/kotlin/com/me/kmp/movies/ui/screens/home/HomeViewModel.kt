package com.me.kmp.movies.ui.screens.home

import androidx.lifecycle.ViewModel
import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.utils.ResultObject
import com.me.kmp.movies.utils.launchFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movie = MutableStateFlow<ResultObject<List<MovieModel>>>(ResultObject.Loading())
    val movie: StateFlow<ResultObject<List<MovieModel>>> = _movie.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
//        viewModelScope.launch {
//            try {
//                val movies = moviesRepository.fetchPopularMovies(region = "US").movies
//                _movie.value = ResultObject.success(movies)
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//                _movie.value = ResultObject.error(exception)
//            }
//        }

        launchFlow(stateFlow = _movie) {
            moviesRepository.movies
        }
    }
}

