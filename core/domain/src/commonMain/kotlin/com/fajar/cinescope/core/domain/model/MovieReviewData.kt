package com.fajar.cinescope.core.domain.model


data class MovieReviewData(
    val comment: String,
    val postedTimestamp: Long,
    val reviewerUsername: String
)