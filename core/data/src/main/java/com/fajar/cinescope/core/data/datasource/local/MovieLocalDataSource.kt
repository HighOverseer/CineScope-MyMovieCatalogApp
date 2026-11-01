package com.fajar.cinescope.core.data.datasource.local

import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.MovieDetailData
import kotlinx.coroutines.flow.Flow

internal interface MovieLocalDataSource {
    fun getListFavoriteMovies(): Flow<List<FavoriteMovieItemData>>
    suspend fun addFavoriteMovie(movieDetailData: MovieDetailData)
    suspend fun deleteFromListFavorite(movieId: Long)
}