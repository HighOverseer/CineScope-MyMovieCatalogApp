package com.fajar.cinescope.movie_detail.presentation.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.utils.shimmeringEffect

@Composable
internal fun ShimmeringMovieDetailContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(bottom = 40.25.dp)

    ) {
        val imageAspectRatio = 1.652f
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(imageAspectRatio)
                    .align(Alignment.Center)
                    .shimmeringEffect(),
            )
        }

        Spacer(Modifier.height(24.dp))

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(17.dp)
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(17.dp)
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmeringEffect()
            )


            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(24.dp)
                    .shimmeringEffect()
            )
        }
    }
}

@Preview
@Composable
private fun ShimmeringMovieDetailContentPreview() {
    CineScopeAppTheme {
        ShimmeringMovieDetailContent()
    }
}