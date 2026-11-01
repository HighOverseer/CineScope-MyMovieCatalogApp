package com.fajar.cinescope.movie_detail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Grey47
import com.fajar.cinescope.movie_detail.presentation.R
import kotlin.math.floor
import com.fajar.cinescope.core.ui.R as CoreR

@Composable
internal fun MovieRatingSection(
    modifier: Modifier = Modifier,
    relativeScore: Double,
    maxScore: Double,
    displayRating: String,
    textSize: TextUnit = 16.sp,
    starSize: Dp = 16.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val fullStarsCount = floor(relativeScore).toInt()
        val shouldAddHalfStar = relativeScore % 1.0 != 0.0
        val emptyStarsCount = floor(
            maxScore - relativeScore
        ).toInt()

        val starComposable = remember {
            movableContentOf<Int> { starDrawableResId ->
                Image(
                    modifier = Modifier
                        .size(starSize),
                    painter = painterResource(starDrawableResId),
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(R.string.rate_star)
                )
            }
        }
        repeat(fullStarsCount) {
            starComposable(CoreR.drawable.ic_star)
        }
        if (shouldAddHalfStar) {
            starComposable(CoreR.drawable.ic_half_star)
        }
        repeat(emptyStarsCount) {
            starComposable(CoreR.drawable.ic_empty_star)
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = displayRating,
            style = MaterialTheme.typography.labelMedium,
            color = Grey47,
            fontSize = textSize
        )
    }
}

@Preview
@Composable
private fun MovieRatingSectionPreview() {
    CineScopeAppTheme {
        MovieRatingSection(
            relativeScore = 8.5,
            maxScore = 10.0,
            displayRating = "8.5/10"
        )
    }
}