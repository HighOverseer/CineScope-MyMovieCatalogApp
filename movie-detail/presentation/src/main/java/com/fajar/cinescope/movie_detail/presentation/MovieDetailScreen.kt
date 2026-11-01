package com.fajar.cinescope.movie_detail.presentation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.components.GeneralDialog
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Grey68
import com.fajar.cinescope.core.ui.theme.Grey90
import com.fajar.cinescope.core.ui.theme.Red50
import com.fajar.cinescope.core.ui.utils.startShareIntent
import com.fajar.cinescope.movie_detail.presentation.model.MovieDetailDUI
import com.fajar.cinescope.movie_detail.presentation.model.MovieReviewDUI
import com.fajar.cinescope.movie_detail.presentation.section.MovieDetailMainContent
import com.fajar.cinescope.movie_detail.presentation.section.ShimmeringMovieDetailContent
import kotlinx.collections.immutable.toImmutableList
import com.fajar.cinescope.core.ui.R as CoreR

@Composable
internal fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = { }
) {
    LaunchedEffect(Unit) {
        viewModel.initializeIfNeeded()
    }

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    MovieDetailScreen(
        modifier = Modifier,
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onToggleFavoriteStatus = viewModel::toggleFavoriteStatus,
    )
}

@Composable
private fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailUIState,
    onNavigateUp: () -> Unit = { },
    onToggleFavoriteStatus: () -> Unit = { }
) {
    val scrollState = rememberScrollState()

    uiState.errorUserMessage?.let { errorUserMessage ->
        GeneralDialog(
            title = stringResource(R.string.error),
            message = errorUserMessage.getValue(),
            onClick = onNavigateUp
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Grey90)
            .verticalScroll(scrollState),
    ) {

        Spacer(Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onNavigateUp
            ) {
                Icon(
                    modifier = Modifier
                        .width(21.dp)
                        .height(20.dp),
                    imageVector = ImageVector
                        .vectorResource(CoreR.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = Black10
                )
            }

            Spacer(Modifier.weight(1f))

            val context = LocalContext.current
            IconButton(
                onClick = { onShareMovie(context, movieDetailDUI = uiState.movieDetailDUI) }
            ) {
                Icon(
                    imageVector = ImageVector
                        .vectorResource(R.drawable.ic_share),
                    contentDescription = stringResource(R.string.share),
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = onToggleFavoriteStatus
            ) {
                Icon(
                    imageVector = ImageVector
                        .vectorResource(CoreR.drawable.ic_fav),
                    contentDescription = if (uiState.isFavorite) {
                        stringResource(R.string.remove_from_favorite_list)
                    } else stringResource(R.string.add_to_favorite_list),
                    tint = if (uiState.isFavorite) Red50 else Grey68
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        if (uiState.isLoading) {
            ShimmeringMovieDetailContent()
        } else MovieDetailMainContent(
            dui = uiState.movieDetailDUI,
        )
    }
}


private fun onShareMovie(
    context: Context,
    movieDetailDUI: MovieDetailDUI
) {

    val shareText = context.getString(
        R.string.share_movie_intent_text,
        movieDetailDUI.title,
        movieDetailDUI.overview,
        movieDetailDUI.id.toString()
    ).trimIndent()

    context.startShareIntent(
        subject = movieDetailDUI.title,
        text = shareText,
        chooserTitle = context.getString(R.string.share_movie)
    )

}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenPreview() {
    CineScopeAppTheme {
        MovieDetailScreen(
            uiState = MovieDetailUIState(
                isLoading = false,
                errorUserMessage = null,
                movieDetailDUI = MovieDetailDUI(
                    title = "Blade Runner 2049",
                    overview = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum  Lorem ipsum Lorem ipsum",
                    rating = Rating(8.5, 10.0, null),
                    imageUrl = "image.com",
                    reviews = remember {
                        List(5) {
                            MovieReviewDUI(
                                comment = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem",
                                displayPostedDate = "12 Jan 2022",
                                reviewerUsername = "andidaud90"
                            )
                        }.toImmutableList()
                    }
                )
            )
        )
    }
}