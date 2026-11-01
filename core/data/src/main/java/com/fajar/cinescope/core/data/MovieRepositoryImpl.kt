package com.fajar.cinescope.core.data

import com.fajar.cinescope.core.data.datasource.local.MovieLocalDataSource
import com.fajar.cinescope.core.data.datasource.remote.MovieRemoteDataSource
import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getMoviePreviews(movieCategory: MovieCategory): Result<List<MoviePreviewsItemData>, DataError> {
        return remoteDataSource.getMoviePreviews(movieCategory)
    }

    override fun getListFavoriteMovies(): Flow<List<FavoriteMovieItemData>> {
        return localDataSource.getListFavoriteMovies()
    }

    override suspend fun addFavoriteMovie(movieDetailData: MovieDetailData) {
        return localDataSource.addFavoriteMovie(movieDetailData)
    }

    override suspend fun deleteFromListFavorite(movieId: Long) {
        return localDataSource.deleteFromListFavorite(movieId)
    }
}