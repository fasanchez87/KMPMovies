package com.me.kmp.movies.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.me.kmp.movies.routes.DetailHomeRoute
import com.me.kmp.movies.routes.HomeRoute
import com.me.kmp.movies.ui.screens.detail.DetailScreen
import com.me.kmp.movies.ui.screens.home.HomeScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
//
//@OptIn(KoinExperimentalAPI::class)
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = HomeRoute,
//    ) {
//        composable<HomeRoute> {
////            HomeScreen(
////                onMovieClick = { movie ->
////                    navController.navigate(
////                        DetailHomeRoute(
////                            id = movie.id,
////                        ),
////                    )
////                }
////            )
//        }
//        composable<DetailHomeRoute> { backStackEntry ->
//            val movie = backStackEntry.toRoute<DetailHomeRoute>()
//            DetailScreen(
//                viewModel = koinViewModel(parameters = { parametersOf(movie.id) }),
//                onBack = { navController.popBackStack() }
//            )
//        }
//    }
//}
