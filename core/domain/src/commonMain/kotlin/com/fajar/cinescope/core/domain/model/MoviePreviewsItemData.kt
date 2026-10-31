package com.fajar.cinescope.core.domain.model

data class MoviePreviewsItemData(
    val id: Long,
    val title: String,
    val thumbnailImageUrl: String,
    val rating: Rating?
)