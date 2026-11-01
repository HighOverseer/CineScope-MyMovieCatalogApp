package com.fajar.cinescope.movie_detail.presentation.mapper

import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.MovieReviewData
import com.fajar.cinescope.core.ui.utils.formatter.DefaultDUIDateFormatter
import com.fajar.cinescope.movie_detail.presentation.model.MovieDetailDUI
import com.fajar.cinescope.movie_detail.presentation.model.MovieReviewDUI
import kotlinx.collections.immutable.toImmutableList

internal class DUIMapper(
    private val duiDateFormatter: DefaultDUIDateFormatter
) {
    fun mapReviewToDUI(
        movieReviewData: MovieReviewData
    ): MovieReviewDUI {
        movieReviewData.run {
            return MovieReviewDUI(
                comment = comment,
                displayPostedDate = duiDateFormatter
                    .formatToDisplayDate(postedTimestamp),
                reviewerUsername = reviewerUsername
            )
        }
    }

    fun mapDetailToDUI(
        movieDetailData: MovieDetailData
    ): MovieDetailDUI {
        return movieDetailData.run {
            MovieDetailDUI(
                id = id,
                title = title,
                imageUrl = imageUrl,
                overview = overview,
                rating = rating,
                reviews = reviews.map(::mapReviewToDUI).toImmutableList()
            )
        }
    }
}