package com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper

import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.MoviePreviewsResponseItemDTO
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Rating

internal class MapperToDomain(
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
                rating = voteAverage?.let { Rating(relativeScore = it, maxScore = 10.0, count = voteCount) },
            )
        }
    }
}