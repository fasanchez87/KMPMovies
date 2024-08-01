package com.me.kmp.movies.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.me.kmp.movies.domain.model.MovieModel
import com.me.kmp.movies.ui.screens.Screen
import com.me.kmp.movies.ui.screens.home.StateHandler
import kmpmovies.composeapp.generated.resources.Res
import kmpmovies.composeapp.generated.resources.back
import kmpmovies.composeapp.generated.resources.original_language
import kmpmovies.composeapp.generated.resources.original_title
import kmpmovies.composeapp.generated.resources.popularity
import kmpmovies.composeapp.generated.resources.release_date
import kmpmovies.composeapp.generated.resources.vote_average

import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    Screen {
        StateHandler(
            onLoading = { null },
            stateFlow = viewModel.movie,
            onSuccess = { movie ->
                DrawDetailScreen(
                    onBack = onBack,
                    movie = movie,
                    viewModel = viewModel
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawDetailScreen(
    onBack: () -> Unit,
    movie: MovieModel,
    viewModel: DetailViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isFavorite = remember { mutableStateOf(movie.isFavorite) }

    Scaffold(
        topBar = {
            TopBar(
                title = movie.title,
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isFavorite.value = !isFavorite.value
                viewModel.setFavorite(movie.copy(isFavorite = isFavorite.value))
            }){
                Icon(
                    imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = movie.backdropPath ?: movie.posterPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            )
            Text(
                text = movie.overview,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = buildAnnotatedString {
                    property(
                        name = stringResource(Res.string.original_language),
                        value = movie.originalLanguage
                    )
                    property(
                        name = stringResource(Res.string.original_title),
                        value = movie.originalTitle
                    )
                    property(
                        name = stringResource(Res.string.popularity),
                        value = movie.popularity.toString()
                    )
                    property(
                        name = stringResource(Res.string.release_date),
                        value = movie.releaseDate
                    )
                    property(
                        name = stringResource(Res.string.vote_average),
                        value = movie.voteAverage.toString(),
                        endString = true
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(16.dp)
            )
        }
    }
}

private fun AnnotatedString.Builder.property(
    name: String,
    value: String,
    endString: Boolean = false
) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!endString) {
            append("\n")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}
