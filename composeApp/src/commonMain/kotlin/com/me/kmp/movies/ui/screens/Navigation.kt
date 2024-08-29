package com.me.kmp.movies.ui.screens

//
// @OptIn(KoinExperimentalAPI::class)
// @Composable
// fun Navigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = HomeRoute,
//    ) {
//        composable<HomeRoute> {
// //            HomeScreen(
// //                onMovieClick = { movie ->
// //                    navController.navigate(
// //                        DetailHomeRoute(
// //                            id = movie.id,
// //                        ),
// //                    )
// //                }
// //            )
//        }
//        composable<DetailHomeRoute> { backStackEntry ->
//            val movie = backStackEntry.toRoute<DetailHomeRoute>()
//            DetailScreen(
//                viewModel = koinViewModel(parameters = { parametersOf(movie.id) }),
//                onBack = { navController.popBackStack() }
//            )
//        }
//    }
// }
