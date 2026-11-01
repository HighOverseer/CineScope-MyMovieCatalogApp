package com.fajar.cinescope.movie_previews.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajar.cinescope.movie_previews.presentation.ListMoviePreviewsScreen

fun NavGraphBuilder.listMoviePreviewsNavGraph(
    openMovieDetail: (Long) -> Unit
) {
    navigation<ListMoviePreviewsNavigation.Graph>(
        startDestination = ListMoviePreviewsNavigation.ListPreviewsRoute
    ) {
        composable<ListMoviePreviewsNavigation.ListPreviewsRoute> {
            ListMoviePreviewsScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetail = openMovieDetail
            )
        }
    }
}