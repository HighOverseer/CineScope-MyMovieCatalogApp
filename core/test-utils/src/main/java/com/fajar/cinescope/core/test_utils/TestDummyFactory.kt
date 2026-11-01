package com.fajar.cinescope.core.test_utils

import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.MovieReviewData
import com.fajar.cinescope.core.domain.model.Rating

object TestDummyFactory {
    val dummyRating = Rating(
        relativeScore = 8.5,
        maxScore = 10.0,
        count = 100
    )

    val dummyMoviePreviewsItem = MoviePreviewsItemData(
        id = 1L,
        title = "Test Movie",
        thumbnailImageUrl = "https://image.com/thumb.jpg",
        rating = dummyRating
    )

    val dummyMovieReview = MovieReviewData(
        comment = "Great movie!",
        postedTimestamp = 123456789L,
        reviewerUsername = "John Doe"
    )

    val dummyMovieDetail = MovieDetailData(
        id = 1L,
        title = "Test Movie",
        imageUrl = "https://image.com/poster.jpg",
        overview = "Test movie overview description.",
        rating = dummyRating,
        reviews = listOf(dummyMovieReview)
    )

    val dummyFavoriteMovieItem = FavoriteMovieItemData(
        id = 1L,
        title = "Test Movie",
        imageUrl = "https://image.com/poster.jpg",
        rating = dummyRating,
        overview = "Test movie overview description."
    )
}
