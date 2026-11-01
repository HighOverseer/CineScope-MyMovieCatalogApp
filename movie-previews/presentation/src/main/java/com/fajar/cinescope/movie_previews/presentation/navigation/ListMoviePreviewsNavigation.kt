package com.fajar.cinescope.movie_previews.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ListMoviePreviewsNavigation {
    @Serializable
    data object Graph

    @Serializable
    data object ListPreviewsRoute
}