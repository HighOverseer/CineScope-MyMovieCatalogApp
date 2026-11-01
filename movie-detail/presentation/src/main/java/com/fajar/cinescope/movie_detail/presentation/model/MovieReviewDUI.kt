package com.fajar.cinescope.movie_detail.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class MovieReviewDUI(
    val comment: String = "-",
    val displayPostedDate: String = "-",
    val reviewerUsername: String = "-"
)