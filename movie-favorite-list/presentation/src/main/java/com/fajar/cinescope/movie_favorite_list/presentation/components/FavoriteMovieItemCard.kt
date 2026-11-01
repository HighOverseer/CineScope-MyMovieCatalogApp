package com.fajar.cinescope.movie_favorite_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.components.BaseAsyncImage
import com.fajar.cinescope.core.ui.section.RatingRowSection
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.Black50
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Orange85
import com.fajar.cinescope.core.ui.utils.formatDecimalPlaces
import com.fajar.cinescope.core.ui.utils.shimmeringEffect
import com.fajar.cinescope.core.ui.R as CoreR

@Composable
fun FavoriteMovieItemCard(
    modifier: Modifier = Modifier,
    item: FavoriteMovieItemData,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .height(140.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
                    .fillMaxSize()
            ) {
                BaseAsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageUrlOrPath = item.imageUrl,
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        lineHeight = 16.sp,
                        letterSpacing = 0.01.sp
                    ),
                    color = Black10,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingRowSection(
                        displayRating = item.rating?.normalizedRelativeScore
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
                            text = stringResource(CoreR.string.ratings, item.rating?.count ?: "-"),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    textAlign = TextAlign.Justify,
                    text = item.overview,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Black50,
                    lineHeight = 16.sp,
                    minLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}

@Composable
fun FavoriteMovieItemCardLoading(modifier: Modifier = Modifier) {
    val shimmeringBoxModifier = remember {
        modifier
            .height(17.dp)
            .shimmeringEffect()
    }

    Card(
        modifier = modifier
            .height(140.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmeringEffect()
                )
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Box(
                    modifier = shimmeringBoxModifier
                        .fillMaxWidth()
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                repeat(2) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(Modifier.width(8.dp))

                        Box(modifier = shimmeringBoxModifier.fillMaxWidth())

                    }

                    Spacer(Modifier.height(8.dp))
                }
            }

        }
    }
}

@Preview
@Composable
private fun FavoriteMovieItemCardPreview() {
    CineScopeAppTheme {
        FavoriteMovieItemCard(
            item = FavoriteMovieItemData(
                id = 0,
                title = "Blade Runner 2049",
                imageUrl = "image.com",
                rating = Rating(relativeScore = 8.0, maxScore = 10.0, count = 1000),
                overview = "Lorem ipsum alskflsmflmskdf ksflks ksfldfl kskmf hjkjwejwjfnwjkn wkjenjkwnjkernjk  kwnejkrnwkjrnwkjn kjwnerkjnwjkrn werjk",
            )
        )
    }

}

@Preview
@Composable
private fun FavoriteMovieItemCardLoadingPreview() {
    CineScopeAppTheme {
        FavoriteMovieItemCardLoading()
    }
}