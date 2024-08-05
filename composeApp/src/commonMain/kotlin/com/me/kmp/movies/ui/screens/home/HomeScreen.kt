package com.me.kmp.movies.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.me.kmp.movies.ErrorDialog
import com.me.kmp.movies.ItemMovie
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.ui.common.PermissionRequestEffect
import com.me.kmp.movies.ui.screens.Screen
import com.me.kmp.movies.utils.ResultObject
import dev.icerock.moko.permissions.Permission
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.app_name
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    onMovieClick: (MovieModel) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(Res.string.app_name))
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { padding ->

            PermissionRequestEffect(Permission.COARSE_LOCATION) { isGranted ->
                viewModel.getMovies()
            }

            StateHandler(
                stateFlow = viewModel.movie,
                // onLoading = { ShowLoading() },
                onSuccess = { movies -> MoviesList(movies, onMovieClick, padding) },
                // onError = { exception -> ShowErrorMessage(exception) },
                // onEmpty = { ShowDataEmptyMessage("No movies found") }
            )
        }
    }
}

@Composable
fun ShowLoading() {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .pointerInput(Unit) {},
    ) {
        CircularProgressIndicator(
            color = Color(0xFF2E2E2E),
            modifier =
            Modifier
                .size(100.dp)
                .clip(CircleShape)
                .align(Alignment.Center),
        )
    }
}

@Composable
fun ShowDataEmptyMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Data is empty", color = Color.Red)
    }
}

@Composable
fun MoviesList(
    movies: List<MovieModel>,
    onMovieClick: (MovieModel) -> Unit,
    padding: PaddingValues,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding =
        PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 4.dp,
            bottom = 4.dp,
        ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(padding),
    ) {
        items(
            items = movies,
            key = { movie ->
                movie.id
            },
        ) {
            ItemMovie(
                movie = it,
                onMovieClick = { onMovieClick(it) },
            )
        }
    }
}

@Composable
fun <T> StateHandler(
    stateFlow: StateFlow<ResultObject<T>>,
    onLoading: @Composable () -> Unit? = { ShowLoading() },
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (Exception) -> Unit? = {
        ErrorDialog(
            description = it.message ?: "Error unknown"
        )
    },
    onEmpty: @Composable () -> Unit? = { },
) {
    val state by stateFlow.collectAsStateWithLifecycle()

    when (state) {
        is ResultObject.Loading -> onLoading()
        is ResultObject.Success -> onSuccess((state as ResultObject.Success<T>).data)
        is ResultObject.Error -> onError((state as ResultObject.Error<T>).exception)
        is ResultObject.Empty -> onEmpty()
    }
}
