package com.me.kmp.movies.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.me.kmp.movies.data.repository.MoviesRepository
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.utils.ResultObject
import com.me.kmp.movies.utils.launchFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal fun InstanceKeeperOwner.viewModelStoreOwner(): ViewModelStoreOwner =
    instanceKeeper.getOrCreate(::ViewModelStoreOwnerInstance)

private class ViewModelStoreOwnerInstance : ViewModelStoreOwner, InstanceKeeper.Instance {

    override val viewModelStore: ViewModelStore = ViewModelStore()

    override fun onDestroy() {
        viewModelStore.clear()
    }
}

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
