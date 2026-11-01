package com.fajar.cinescope.core.domain.repository

import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviePreviews(
        movieCategory: MovieCategory
    ): Result<List<MoviePreviewsItemData>, DataError>

    suspend fun getMovieDetail(
        selectedMovieId: Long
    ): Result<MovieDetailData, DataError>

    fun getListFavoriteMovies(): Flow<List<FavoriteMovieItemData>>
    fun checkIsMovieInFavoriteList(movieId: Long): Flow<Boolean>
    suspend fun addFavoriteMovie(movieDetailData: MovieDetailData)
    suspend fun deleteFromListFavorite(movieId: Long)
}