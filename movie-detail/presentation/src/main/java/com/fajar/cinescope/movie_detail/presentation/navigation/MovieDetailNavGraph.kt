package com.fajar.cinescope.movie_detail.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.fajar.cinescope.core.ui.utils.navigateUpSafely
import com.fajar.cinescope.movie_detail.presentation.MovieDetailScreen
import com.fajar.cinescope.movie_detail.presentation.MovieDetailViewModel

fun NavGraphBuilder.movieDetailNavGraph(
    navController: NavHostController
) {
    navigation<MovieDetailNavigation.Graph>(
        startDestination = MovieDetailNavigation.MovieDetailRoute
    ) {
        composable<MovieDetailNavigation.MovieDetailRoute> { entry ->
            val navGraphBackStackEntry = remember(entry) {
                navController.getBackStackEntry<MovieDetailNavigation.Graph>()
            }
            val navGraphRoute = navGraphBackStackEntry.toRoute<MovieDetailNavigation.Graph>()
            val viewModel: MovieDetailViewModel = hiltViewModel(
                creationCallback = { factory: MovieDetailViewModel.Factory ->
                    factory.create(navGraphRoute.movieId)
                }
            )

            MovieDetailScreen(
                viewModel = viewModel,
                onNavigateUp = navController::navigateUpSafely
            )
        }
    }
}