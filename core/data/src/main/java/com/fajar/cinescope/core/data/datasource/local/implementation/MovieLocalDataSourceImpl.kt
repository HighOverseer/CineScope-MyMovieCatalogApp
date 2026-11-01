package com.fajar.cinescope.core.data.datasource.local.implementation

import com.fajar.cinescope.core.data.datasource.local.MovieLocalDataSource
import com.fajar.cinescope.core.data.datasource.local.implementation.db.FavoriteMovieDao
import com.fajar.cinescope.core.data.datasource.local.implementation.db.FavoriteMovieEntity
import com.fajar.cinescope.core.data.datasource.local.implementation.mapper.LocalDataMapperToDomain
import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.MovieDetailData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieLocalDataSourceImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val localDataMapperToDomain: LocalDataMapperToDomain
) : MovieLocalDataSource {
    override fun getListFavoriteMovies(): Flow<List<FavoriteMovieItemData>> {
        return favoriteMovieDao.getListFavoriteMovies()
            .map { currentList ->
                currentList.map(localDataMapperToDomain::mapEntityToFavoriteDao)
            }
    }

    override suspend fun addFavoriteMovie(movieDetailData: MovieDetailData) {
        return favoriteMovieDao.insertFavoriteMovie(
            entity = FavoriteMovieEntity(
                id = movieDetailData.id,
                title = movieDetailData.title,
                imageUrl = movieDetailData.imageUrl,
                overview = movieDetailData.overview,
                relativeScore = movieDetailData.rating?.normalizedRelativeScore,
                maxScore = movieDetailData.rating?.maxScore,
                count = movieDetailData.rating?.count
            )
        )
    }

    override suspend fun deleteFromListFavorite(movieId: Long) {
        return favoriteMovieDao.deleteFromListFavorite(movieId)
    }

    override fun checkIsMovieInFavoriteList(movieId: Long): Flow<Boolean> {
        return favoriteMovieDao.checkIfMovieInFavoriteList(movieId)
    }
}