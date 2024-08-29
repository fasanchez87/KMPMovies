package com.me.kmp.movies.utils

import com.me.kmp.movies.ui.screens.home.HomeViewModel
import com.me.kmp.movies.utils.di.testModule
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class LaunchDataTest : KoinTest {

    private val viewModel: HomeViewModel by inject()

    @Before
    fun setup() {
        startKoin {
            startKoin {
                modules(
                    testModule
                )
            }
        }

        @After
        fun tearDown() {
            stopKoin()
        }

//    @Test
//    fun `test launchFlow with successful data emission`() = runTest {
//        val data = listOf(MovieModel(/* ... */))
//        val flow = flowOf(data)
//
//        // Configura el repositorio para que devuelva el flujo deseado
//        val repository: MoviesRepository by inject()
//        coEvery { repository.movies } returns flow
//
//        viewModel.getMovies()
//
//        assertEquals(ResultObject.success(data), viewModel.movie.value)
//    }
    }
}
