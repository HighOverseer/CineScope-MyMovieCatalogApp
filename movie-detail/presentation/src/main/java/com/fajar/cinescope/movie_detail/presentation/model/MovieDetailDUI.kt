package com.fajar.cinescope.movie_detail.presentation.model

import androidx.compose.runtime.Immutable
import com.fajar.cinescope.core.domain.model.Rating
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class MovieDetailDUI(
    val id: Long = 0,
    val title: String = "-",
    val imageUrl: String = "",
    val overview: String = "-",
    val rating: Rating? = null,
    val reviews: ImmutableList<MovieReviewDUI> = persistentListOf()
)