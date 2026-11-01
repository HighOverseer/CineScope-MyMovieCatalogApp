package com.fajar.cinescope.movie_favorite_list.presentation.navigation

import kotlinx.serialization.Serializable


sealed interface ListFavoriteMoviesNavigation {
    @Serializable
    data object Graph

    @Serializable
    data object ListRoute
}