package com.fajar.cinescope.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.fajar.cinescope.core.ui.R

@Composable
fun BaseAsyncImage(
    modifier: Modifier = Modifier,
    imageUrlOrPath: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null
) {
    AsyncImage(
        modifier = modifier,
        model = imageUrlOrPath,
        contentScale = contentScale,
        contentDescription = contentDescription,
        error = painterResource(R.drawable.ic_image_loading_error_default),
        placeholder = painterResource(R.drawable.ic_image_placeholder),
        fallback = painterResource(R.drawable.ic_image_placeholder)
    )
}