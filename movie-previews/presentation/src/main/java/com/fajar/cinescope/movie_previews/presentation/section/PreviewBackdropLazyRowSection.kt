package com.fajar.cinescope.movie_previews.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.utils.shimmeringEffect
import com.fajar.cinescope.movie_previews.presentation.ListPreviewsDataState
import com.fajar.cinescope.movie_previews.presentation.SectionHeadline
import com.fajar.cinescope.movie_previews.presentation.components.MoviePreviewItemBackdropCard
import com.fajar.cinescope.core.ui.R as CoreR


@Composable
internal fun PreviewBackdropLazyRowSection(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    leadingIconResId: Int,
    onItemClicked: (MoviePreviewsItemData) -> Unit = { },
    state: ListPreviewsDataState,
    onRefresh: () -> Unit = { }
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

        if (state is ListPreviewsDataState.Error)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RetryLoadingSection(
                    errorMessage = state.errorUIText?.getValue()
                        ?: stringResource(CoreR.string.an_error_occurred_please_try_again),
                    onRetry = onRefresh
                )
            }
        else
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = topRatedPreviewsListState
            ) {
                when (state) {
                    is ListPreviewsDataState.Loading -> {
                        items(count = 5) {
                            ShimmeringMoviePreviewItemBackdropCard()
                        }
                    }

                    is ListPreviewsDataState.Loaded -> {
                        items(state.previewsData, { it.id }) { item ->
                            MoviePreviewItemBackdropCard(
                                onClick = { onItemClicked(item) },
                                dui = item,
                            )
                        }
                    }

                    is ListPreviewsDataState.Error -> Unit
                }
            }
    }
}

@Composable
private fun ShimmeringMoviePreviewItemBackdropCard(modifier: Modifier = Modifier) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val cardModifier = remember(density) {
        val imageToParentAspectRatio = 0.8f
        val screenWidth = with(density) { containerSize.width.toDp() }
        val sizeMin = screenWidth * imageToParentAspectRatio
        modifier
            .width(sizeMin)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = cardModifier,
    ) {
        val imageModifier = remember {
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .aspectRatio(1.8f)
        }
        Box(
            modifier = imageModifier,
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .shimmeringEffect()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ShimmeringMoviePreviewItemBackdropCardPreview() {
    CineScopeAppTheme {
        Column(Modifier.fillMaxSize()) {
            ShimmeringMoviePreviewItemBackdropCard()
        }
    }
}