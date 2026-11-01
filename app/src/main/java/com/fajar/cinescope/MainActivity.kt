package com.fajar.cinescope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.utils.navigateSafely
import com.fajar.cinescope.movie_detail.presentation.navigation.MovieDetailNavigation
import com.fajar.cinescope.movie_detail.presentation.navigation.movieDetailNavGraph
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
                ){
                    listMoviePreviewsNavGraph(
                        openMovieDetail = { movieId ->
                            navController.navigateSafely(
                                MovieDetailNavigation.Graph(
                                    movieId = movieId
                                )
                            )
                        }
                    )
                    movieDetailNavGraph(navController = navController)
                }
            }
        }
    }
}
