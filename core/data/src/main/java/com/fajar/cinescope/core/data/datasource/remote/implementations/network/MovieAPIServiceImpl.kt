package com.fajar.cinescope.core.data.datasource.remote.implementations.network

import com.fajar.cinescope.core.data.datasource.remote.implementations.network.common.DefaultAPIServiceScope
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetListMoviePreviewsResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class MovieAPIServiceImpl(
    private val httpClient: HttpClient
) : MovieAPIService, DefaultAPIServiceScope() {

    override suspend fun getPopularMoviePreviews(): GetListMoviePreviewsResponseDTO {
        return httpClient.get(ENDPOINTS.GET_POPULAR_MOVIE_PREVIEWS.endpointPath)
            .getBodyIfNotSuccessThrowHttpException()
    }

    override suspend fun getTopRatedMoviePreviews(): GetListMoviePreviewsResponseDTO {
        return httpClient.get(ENDPOINTS.GET_TOP_RATED_MOVIE_PREVIEWS.endpointPath)
            .getBodyIfNotSuccessThrowHttpException()
    }

    override suspend fun getNowPlayingMoviePreviews(): GetListMoviePreviewsResponseDTO {
        return httpClient.get(ENDPOINTS.GET_NOW_PLAYING_MOVIE_PREVIEWS.endpointPath)
            .getBodyIfNotSuccessThrowHttpException()
    }

    private enum class ENDPOINTS(val endpointPath: String) {
        GET_POPULAR_MOVIE_PREVIEWS("popular"),
        GET_TOP_RATED_MOVIE_PREVIEWS("top_rated"),
        GET_NOW_PLAYING_MOVIE_PREVIEWS("now_playing")
    }
}