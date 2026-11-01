package com.fajar.cinescope.movie_previews.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.components.BaseAsyncImage
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Orange85
import com.fajar.cinescope.core.ui.utils.formatDecimalPlaces
import com.fajar.cinescope.movie_previews.presentation.R
import com.fajar.cinescope.movie_previews.presentation.section.RatingRowSection

@Composable
internal fun MoviePreviewItemPosterCard(
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingRowSection(
                    displayRating = dui.rating?.normalizedRelativeScore
                        ?.formatDecimalPlaces() ?: "-",
                )

                Spacer(Modifier.width(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Orange85, RoundedCornerShape(2.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = stringResource(R.string.ratings, dui.rating?.count ?: "-"),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp
                    )
                }
            }

        }
    }

}


@Preview(showBackground = false)
@Composable
private fun MoviePreviewItemCardPreviewPoster() {
    CineScopeAppTheme {
        MoviePreviewItemPosterCard(
            dui = MoviePreviewsItemData(
                id = 1,
                title = "Blade Runner 2049",
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

