package com.fajar.cinescope.core.data.datasource.local.implementation.mapper

import com.fajar.cinescope.core.data.datasource.local.implementation.db.FavoriteMovieEntity
import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.Rating

internal class LocalDataMapperToDomain {

    fun mapEntityToFavoriteDao(
        entity: FavoriteMovieEntity
    ): FavoriteMovieItemData {
        return entity.run {
            val rating = run {
                Rating(
                    relativeScore = relativeScore ?: return@run null,
                    maxScore = maxScore ?: return@run null,
                    count = count
                )
            }

            FavoriteMovieItemData(
                id = id,
                title = title,
                imageUrl = imageUrl,
                rating = rating,
                overview = overview
            )
        }
    }
}