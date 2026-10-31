package com.fajar.cinescope.movie_previews.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.components.BaseAsyncImage
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Grey47
import com.fajar.cinescope.core.ui.theme.Orange85
import com.fajar.cinescope.core.ui.utils.shimmeringEffect
import com.fajar.cinescope.movie_previews.presentation.R as ThisR

@Composable
internal fun MoviePreviewItemCard(
    modifier: Modifier = Modifier,
    dui: MoviePreviewsItemData,
    onClick: (MoviePreviewsItemData) -> Unit = { }
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val cardModifier = remember(density) {
        val imageToParentAspectRatio = 0.44f
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
        onClick = { onClick(dui) }
    ) {
        Column(
            Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            val imageModifier = remember {
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .aspectRatio(1.11f)
            }
            Box(
                modifier = imageModifier,
                contentAlignment = Alignment.Center
            ) {
                BaseAsyncImage(
                    modifier = Modifier.align(Alignment.Center),
                    imageUrlOrPath = dui.thumbnailImageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                dui.title,
                style = MaterialTheme.typography.titleMedium,
                color = Black10,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductRatingSection(
                    displayRating = dui.rating?.normalizedRelativeScore?.toString() ?: "-",
                )

                Spacer(Modifier.width(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Orange85, RoundedCornerShape(2.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${dui.rating?.count ?: "-"} Ratings",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
    }

}

@Composable
internal fun ProductRatingSection(
    modifier: Modifier = Modifier,
    displayRating: String,
    textSize: TextUnit = 16.sp,
    starSize: Dp = 16.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val starComposable = remember {
            movableContentOf<Int> { starDrawableResId ->
                Image(
                    modifier = Modifier
                        .offset(y = (-1.5).dp)
                        .size(starSize),
                    painter = painterResource(starDrawableResId),
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(ThisR.string.rate_star)
                )
            }
        }

        Text(
            text = displayRating,
            style = MaterialTheme.typography.labelMedium,
            color = Grey47,
            fontSize = textSize
        )

        Spacer(Modifier.width(4.dp))

        starComposable(ThisR.drawable.ic_star)
    }
}


@Composable
private fun ShimmeringMoviePreviewItemCard(modifier: Modifier = Modifier) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val cardModifier = remember(density) {
        val imageToParentAspectRatio = 0.44f
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
        Column(
            Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            val imageModifier = remember {
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .aspectRatio(1.11f)
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

            Spacer(Modifier.height(16.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(10.dp))

            Box(
                Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .shimmeringEffect()
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun MoviePreviewItemCardPreview() {
    CineScopeAppTheme {
        MoviePreviewItemCard(
            dui = MoviePreviewsItemData(
                id = 1,
                title = "Headset Nexus A1",
                thumbnailImageUrl = "image.com",
                rating = Rating(
                    relativeScore = 8.0,
                    maxScore = 10.0,
                    count = 1080
                ),
            )
        )
    }
}

@Preview
@Composable
private fun ShimmeringMoviePreviewItemCardPreview() {
    CineScopeAppTheme {
        ShimmeringMoviePreviewItemCard()
    }
}