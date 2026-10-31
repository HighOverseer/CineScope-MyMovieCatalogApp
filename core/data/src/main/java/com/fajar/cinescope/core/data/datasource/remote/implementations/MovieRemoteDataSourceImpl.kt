package com.fajar.cinescope.core.data.datasource.remote.implementations

import com.fajar.cinescope.core.common.DispatcherProvider
import com.fajar.cinescope.core.data.datasource.remote.MovieRemoteDataSource
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.MovieAPIService
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.common.callApiFromNetwork
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.MapperToDomain
import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import kotlinx.coroutines.withContext

internal class MovieRemoteDataSourceImpl(
    private val apiService: MovieAPIService,
    private val dispatcherProvider: DispatcherProvider,
    private val mapperToDomain: MapperToDomain
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
                            mapperToDomain.mapToMoviePreviewItemData(
                                itemDto = itemDto,
                                shouldUsePosterPathForImageUrl = false
                            )
                        }
                }
            )
        }
    }
}