package com.fajar.cinescope.movie_previews.presentation.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.movie_previews.presentation.R
import com.fajar.cinescope.movie_previews.presentation.UIState

@Composable
internal fun ListMoviePreviewsMainContent(
    modifier: Modifier = Modifier,
    uiState: UIState = UIState(),
    onNavigateToDetail: (Long) -> Unit = { },
    refreshListPreviewsData: (MovieCategory?) -> Unit = { }
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
        isRefreshing = false,
        state = pullToRefreshState,
        onRefresh = { refreshListPreviewsData(null) },
        indicator = {
            Indicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                state = pullToRefreshState,
                isRefreshing = false,
                containerColor = Color.White,
                color = Color.Black
            )
        }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            PreviewBackdropLazyRowSection(
                sectionTitle = stringResource(R.string.popular_movie),
                leadingIconResId = R.drawable.ic_fire,
                onItemClicked = { onNavigateToDetail(it.id) },
                state = uiState.popularMoviePreviewsDataState,
                onRefresh = { refreshListPreviewsData(MovieCategory.POPULAR) }
            )

            Spacer(Modifier.height(24.dp))

            PreviewPosterLazyRowSection(
                sectionTitle = stringResource(R.string.top_rated),
                state = uiState.topRatedMoviePreviewsDataState,
                leadingIconResId = R.drawable.ic_glowing_star,
                onItemClicked = { onNavigateToDetail(it.id) },
                onRefresh = { refreshListPreviewsData(MovieCategory.TOP_RATED) }
            )

            Spacer(Modifier.height(24.dp))

            PreviewPosterLazyRowSection(
                sectionTitle = stringResource(R.string.now_playing),
                state = uiState.nowPlayingMoviePreviewsDataState,
                leadingIconResId = R.drawable.ic_musical_note,
                onItemClicked = { onNavigateToDetail(it.id) },
                onRefresh = { refreshListPreviewsData(MovieCategory.NOW_PLAYING) }
            )

            Spacer(Modifier.height(64.dp))
        }
    }
}