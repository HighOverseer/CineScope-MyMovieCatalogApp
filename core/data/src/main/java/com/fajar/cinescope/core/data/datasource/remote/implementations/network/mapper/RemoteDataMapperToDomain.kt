package com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper

import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetMovieOverviewResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetMovieReviewsResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.MoviePreviewsResponseItemDTO
import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.MovieReviewData
import com.fajar.cinescope.core.domain.model.Rating
import kotlin.time.Instant

internal class RemoteDataMapperToDomain(
    private val imageUrlResolver: MovieImageUrlResolver
) {
    fun mapToMoviePreviewItemData(
        itemDto: MoviePreviewsResponseItemDTO,
        shouldUsePosterPathForImageUrl: Boolean
    ): MoviePreviewsItemData? {
        return itemDto.run {
            val imagePath = if (shouldUsePosterPathForImageUrl) posterPath else backdropPath
            val thumbnailImageUrl = imageUrlResolver.resolve(imagePath)
                ?: return null

            MoviePreviewsItemData(
                id = id?.toLong() ?: return null,
                title = title ?: return null,
                thumbnailImageUrl = thumbnailImageUrl,
                rating = voteAverage?.let {
                    Rating(
                        relativeScore = it,
                        maxScore = 10.0,
                        count = voteCount
                    )
                },
            )
        }
    }

    fun mapToMovieDetail(
        overviewDto: GetMovieOverviewResponseDTO,
        reviewsDto: GetMovieReviewsResponseDTO
    ): MovieDetailData? {
        val imageUrl = imageUrlResolver.resolve(overviewDto.backdropPath)
            ?: return null

        val reviews = reviewsDto.results
            ?.filterNotNull()
            ?.mapNotNull {
                val postedTimestamp = it.createdAt?.let { date ->
                    Instant.parseOrNull(date)
                        ?.toEpochMilliseconds()
                }

                MovieReviewData(
                    comment = it.content ?: return@mapNotNull null,
                    postedTimestamp = postedTimestamp ?: return@mapNotNull null,
                    reviewerName = it.authorDetails?.name ?: return@mapNotNull null,
                    reviewerUsername = it.authorDetails.username ?: return@mapNotNull null
                )
            } ?: emptyList()

        return MovieDetailData(
            id = overviewDto.id?.toLong() ?: return null,
            title = overviewDto.title ?: return null,
            imageUrl = imageUrl,
            rating = overviewDto.voteAverage?.let {
                Rating(
                    relativeScore = it,
                    maxScore = 10.0,
                    count = overviewDto.voteCount
                )
            },
            overview = overviewDto.overview ?: "-",
            reviews = reviews
        )
    }
}