package com.fajar.cinescope.movie_favorite_list.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajar.cinescope.movie_favorite_list.presentation.ListFavoriteMoviesScreen

fun NavGraphBuilder.listFavoriteMoviesNavGraph(
    openMovieDetail: (Long) -> Unit,
    onNavigateUp: () -> Unit = { }
) {
    navigation<ListFavoriteMoviesNavigation.Graph>(
        startDestination = ListFavoriteMoviesNavigation.ListRoute
    ) {
        composable<ListFavoriteMoviesNavigation.ListRoute> {
            ListFavoriteMoviesScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetail = openMovieDetail,
                onNavigateUp = onNavigateUp,
            )
        }
    }
}