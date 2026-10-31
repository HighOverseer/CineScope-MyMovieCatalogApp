package com.fajar.cinescope.core.data

import com.fajar.cinescope.core.data.datasource.remote.MovieRemoteDataSource
import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.repository.MovieRepository
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getMoviePreviews(movieCategory: MovieCategory): Result<List<MoviePreviewsItemData>, DataError> {
        return remoteDataSource.getMoviePreviews(movieCategory)
    }
}