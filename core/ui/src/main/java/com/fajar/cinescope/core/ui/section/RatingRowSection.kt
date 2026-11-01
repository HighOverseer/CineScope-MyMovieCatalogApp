package com.fajar.cinescope.core.ui.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.cinescope.core.ui.R
import com.fajar.cinescope.core.ui.theme.Grey47

@Composable
fun RatingRowSection(
    modifier: Modifier = Modifier,
    displayRating: String,
    textSize: TextUnit = 14.sp,
    starSize: Dp = 14.dp
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
                    contentDescription = stringResource(R.string.rate_star)
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

        starComposable(R.drawable.ic_star)
    }
}
