package com.fajar.cinescope.core.domain.model

data class MovieDetailData(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val overview: String,
    val rating: Rating?,
    val reviews: List<MovieReviewData>
)