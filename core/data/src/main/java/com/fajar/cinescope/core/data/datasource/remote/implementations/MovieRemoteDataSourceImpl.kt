package com.fajar.cinescope.core.data.datasource.remote.implementations

import com.fajar.cinescope.core.common.DispatcherProvider
import com.fajar.cinescope.core.data.datasource.remote.MovieRemoteDataSource
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.MovieAPIService
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.common.callApiFromNetwork
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.RemoteDataMapperToDomain
import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: MovieAPIService,
    private val dispatcherProvider: DispatcherProvider,
    private val remoteDataMapperToDomain: RemoteDataMapperToDomain
) : MovieRemoteDataSource {

    override suspend fun getMoviePreviews(movieCategory: MovieCategory): Result<List<MoviePreviewsItemData>, DataError> {
        return withContext(dispatcherProvider.default) {
            callApiFromNetwork(
                execute = {
                    when (movieCategory) {
                        MovieCategory.POPULAR -> apiService.getPopularMoviePreviews()
                        MovieCategory.TOP_RATED -> apiService.getTopRatedMoviePreviews()
                        MovieCategory.NOW_PLAYING -> apiService.getNowPlayingMoviePreviews()
                    }
                },
                mapResponseToResultData = { responseData ->
                    if (responseData.results == null) {
                        return@withContext Result.Error(RootNetworkError.UNEXPECTED_ERROR)
                    }

                    responseData
                        .results
                        .mapNotNull { itemDto ->
                            remoteDataMapperToDomain.mapToMoviePreviewItemData(
                                itemDto = itemDto,
                                shouldUsePosterPathForImageUrl = false
                            )
                        }
                }
            )
        }
    }

    override suspend fun getMovieDetail(
        selectedMovieId: Long
    ): Result<MovieDetailData, DataError> {
        return withContext(dispatcherProvider.default) {
            callApiFromNetwork(
                execute = {
                    val firstJob = async { apiService.getSelectedMovieOverview(selectedMovieId) }
                    val secondJob = async { apiService.getSelectedMovieReview(selectedMovieId) }

                    Pair(
                        first = firstJob.await(),
                        second = secondJob.await()
                    )
                },
                mapResponseToResultData = { (overviewResponseData, reviewsResponseData) ->
                    remoteDataMapperToDomain.mapToMovieDetail(
                        overviewDto = overviewResponseData,
                        reviewsDto = reviewsResponseData
                    ) ?: return@withContext Result.Error(RootNetworkError.UNEXPECTED_ERROR)
                }
            )
        }
    }
}