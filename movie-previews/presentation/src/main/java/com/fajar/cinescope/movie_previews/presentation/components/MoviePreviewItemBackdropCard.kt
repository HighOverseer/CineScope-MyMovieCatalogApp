package com.fajar.cinescope.movie_previews.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.components.BaseAsyncImage
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme

@Composable
internal fun MoviePreviewItemBackdropCard(
    modifier: Modifier = Modifier,
    dui: MoviePreviewsItemData,
    onClick: (MoviePreviewsItemData) -> Unit = { }
) {
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
        onClick = { onClick(dui) }
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
            BaseAsyncImage(
                modifier = Modifier.align(Alignment.Center),
                imageUrlOrPath = dui.thumbnailImageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                text = dui.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun MoviePreviewItemBackdropCardPreview() {
    CineScopeAppTheme {
        Column(Modifier.fillMaxSize()) {
            MoviePreviewItemBackdropCard(
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
}
