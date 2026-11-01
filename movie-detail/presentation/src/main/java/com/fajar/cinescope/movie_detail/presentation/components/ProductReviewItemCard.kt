package com.fajar.cinescope.movie_detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.Grey65
import com.fajar.cinescope.movie_detail.presentation.model.MovieReviewDUI

@Composable
internal fun MovieReviewItemCard(
    modifier: Modifier = Modifier,
    review: MovieReviewDUI
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        onClick = { }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    review.reviewerUsername,
                    style = MaterialTheme.typography.titleMedium,
                    color = Black10,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    review.displayPostedDate,
                    style = MaterialTheme.typography.labelMedium,
                    color = Grey65
                )
            }

            Spacer(
                Modifier.height(8.dp)
            )

            Text(
                text = "\"${review.comment}\"",
                style = MaterialTheme.typography.labelMedium,
                color = Black10,
                fontStyle = FontStyle.Italic
            )
        }
    }
    Spacer(Modifier.height(12.dp))
}