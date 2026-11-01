package com.fajar.cinescope.movie_previews.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Green60
import com.fajar.cinescope.core.ui.theme.Grey90
import com.fajar.cinescope.movie_previews.presentation.section.ListMoviePreviewsMainContent
import kotlinx.collections.immutable.toImmutableList
import com.fajar.cinescope.core.ui.R as CoreR

@Composable
internal fun ListMoviePreviewsScreen(
    viewModel: ListMoviePreviewsViewModel = hiltViewModel(),
    onNavigateToDetail: (Long) -> Unit = { },
    onNavigateToListFavorite: () -> Unit = { }
) {
    LaunchedEffect(Unit) {
        viewModel.refreshListPreviewsData()
    }

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    ListMoviePreviewsScreen(
        modifier = Modifier,
        uiState = uiState,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToListFavorite = onNavigateToListFavorite,
        refreshListPreviewsData = viewModel::refreshListPreviewsData
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListMoviePreviewsScreen(
    modifier: Modifier = Modifier,
    uiState: UIState = UIState(),
    onNavigateToDetail: (Long) -> Unit = { },
    onNavigateToListFavorite: () -> Unit = { },
    refreshListPreviewsData: (MovieCategory?) -> Unit = { }
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { focusManager.clearFocus() }
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    scrolledContainerColor = Color.White,
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.movie_previews),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Black10
                    )
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    CompositionLocalProvider(
                        LocalRippleConfiguration provides RippleConfiguration(
                            color = Color.Black
                        )
                    ) {
                        IconButton(
                            onClick = onNavigateToListFavorite,
                        ) {
                            Icon(
                                imageVector = ImageVector
                                    .vectorResource(CoreR.drawable.ic_fav),
                                contentDescription = stringResource(R.string.navigate_to_list_favorite),
                                tint = Green60
                            )
                        }
                    }
                }
            )

        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ListMoviePreviewsMainContent(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Grey90),
                uiState = uiState,
                onNavigateToDetail = onNavigateToDetail,
                refreshListPreviewsData = refreshListPreviewsData,
            )
        }
    }
}


@Preview
@Composable
private fun ListMoviePreviewsScreenPreview() {
    CineScopeAppTheme {
        val uiState = remember {
            val previewsDataState = ListPreviewsDataState.Loaded(
                List(10) {
                    MoviePreviewsItemData(
                        id = it.toLong(),
                        title = "Blade Runner 2049",
                        thumbnailImageUrl = "image.com",
                        rating = Rating(
                            relativeScore = 8.0,
                            maxScore = 10.0,
                            count = 1080
                        ),
                    )
                }.toImmutableList()
            )

            UIState(
                popularMoviePreviewsDataState = previewsDataState,
                topRatedMoviePreviewsDataState = previewsDataState,
                nowPlayingMoviePreviewsDataState = previewsDataState
            )
        }
        ListMoviePreviewsScreen(uiState = uiState)
    }
}