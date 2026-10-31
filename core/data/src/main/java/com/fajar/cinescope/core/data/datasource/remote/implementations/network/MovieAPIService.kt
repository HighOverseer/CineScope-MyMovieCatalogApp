package com.fajar.cinescope.core.data.datasource.remote.implementations.network

import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetListMoviePreviewsResponseDTO

internal interface MovieAPIService {
    suspend fun getPopularMoviePreviews(): GetListMoviePreviewsResponseDTO
    suspend fun getTopRatedMoviePreviews(): GetListMoviePreviewsResponseDTO
    suspend fun getNowPlayingMoviePreviews(): GetListMoviePreviewsResponseDTO
}