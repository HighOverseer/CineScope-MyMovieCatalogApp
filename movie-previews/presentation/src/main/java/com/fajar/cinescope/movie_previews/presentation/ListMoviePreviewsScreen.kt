package com.fajar.cinescope.movie_previews.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.fajar.cinescope.core.ui.R as CoreR


@Composable
internal fun ListMoviePreviewsScreen(
    modifier: Modifier = Modifier,
    topRatedPreviewsData: ImmutableList<MoviePreviewsItemData> = persistentListOf(),
    nowPlayingPreviewsData: ImmutableList<MoviePreviewsItemData> = persistentListOf(),
    onNavigateToDetail: (Long) -> Unit = { }
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {

        PreviewLazyRowSection(
            sectionTitle = stringResource(R.string.top_rated),
            previewsData = topRatedPreviewsData,
            leadingIconResId = R.drawable.ic_glowing_star
        )

        Spacer(Modifier.height(16.dp))

        PreviewLazyRowSection(

            sectionTitle = stringResource(R.string.now_playing),
            previewsData = topRatedPreviewsData,
            leadingIconResId = R.drawable.ic_musical_note
        )

    }
}

@Composable
fun PreviewLazyRowSection(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    leadingIconResId: Int,
    previewsData: ImmutableList<MoviePreviewsItemData>,
    onItemClicked: (MoviePreviewsItemData) -> Unit = { },
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeadline(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 2.dp),
            leadingIconResId = leadingIconResId,
            trailingIconResId = CoreR.drawable.ic_arrow_right,
            title = sectionTitle.uppercase()
        )

        Spacer(
            Modifier.height(2.dp)
        )

        val topRatedPreviewsListState = rememberLazyListState()

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = topRatedPreviewsListState
        ) {
            items(previewsData, { it.id }) { item ->
                MoviePreviewItemCard(
                    modifier = Modifier
                        .clickable { onItemClicked(item) },
                    dui = item,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ListMoviePreviewsScreenPreview() {
    CineScopeAppTheme {
        ListMoviePreviewsScreen(
            topRatedPreviewsData = remember {
                List(10) {
                    MoviePreviewsItemData(
                        id = it.toLong(),
                        title = "Headset Nexus A1",
                        thumbnailImageUrl = "image.com",
                        rating = Rating(
                            relativeScore = 8.0,
                            maxScore = 10.0,
                            count = 1080
                        ),
                    )
                }.toImmutableList()
            },
            nowPlayingPreviewsData = remember {
                List(10) {
                    MoviePreviewsItemData(
                        id = it.toLong(),
                        title = "Headset Nexus A1",
                        thumbnailImageUrl = "image.com",
                        rating = Rating(
                            relativeScore = 8.0,
                            maxScore = 10.0,
                            count = 1080
                        ),
                    )
                }.toImmutableList()
            }
        )
    }
}