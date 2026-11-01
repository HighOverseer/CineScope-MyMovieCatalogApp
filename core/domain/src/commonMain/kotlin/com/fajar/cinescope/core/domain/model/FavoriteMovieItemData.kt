package com.fajar.cinescope.core.domain.model

data class FavoriteMovieItemData(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val rating: Rating?,
    val overview: String
)