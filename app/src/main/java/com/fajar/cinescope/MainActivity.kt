package com.fajar.cinescope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.utils.navigateSafely
import com.fajar.cinescope.core.ui.utils.navigateUpSafely
import com.fajar.cinescope.movie_detail.presentation.navigation.MovieDetailNavigation
import com.fajar.cinescope.movie_detail.presentation.navigation.movieDetailNavGraph
import com.fajar.cinescope.movie_favorite_list.presentation.navigation.ListFavoriteMoviesNavigation
import com.fajar.cinescope.movie_favorite_list.presentation.navigation.listFavoriteMoviesNavGraph
import com.fajar.cinescope.movie_previews.presentation.navigation.ListMoviePreviewsNavigation
import com.fajar.cinescope.movie_previews.presentation.navigation.listMoviePreviewsNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CineScopeAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ListMoviePreviewsNavigation.Graph
                ) {
                    val openMovieDetail = { movieId: Long ->
                        navController.navigateSafely(
                            MovieDetailNavigation.Graph(
                                movieId = movieId
                            )
                        )
                    }

                    listMoviePreviewsNavGraph(
                        openMovieDetail = openMovieDetail,
                        openListFavoriteMovies = {
                            navController.navigateSafely(
                                route = ListFavoriteMoviesNavigation.Graph
                            )
                        }
                    )

                    movieDetailNavGraph(navController = navController)

                    listFavoriteMoviesNavGraph(
                        openMovieDetail = openMovieDetail,
                        onNavigateUp = navController::navigateUpSafely
                    )
                }
            }
        }
    }
}
