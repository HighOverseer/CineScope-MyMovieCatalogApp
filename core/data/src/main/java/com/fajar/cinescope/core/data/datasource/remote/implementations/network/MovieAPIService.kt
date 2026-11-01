package com.fajar.cinescope.core.data.datasource.remote.implementations.network

import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetListMoviePreviewsResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetMovieOverviewResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetMovieReviewsResponseDTO

internal interface MovieAPIService {
    suspend fun getPopularMoviePreviews(): GetListMoviePreviewsResponseDTO
    suspend fun getTopRatedMoviePreviews(): GetListMoviePreviewsResponseDTO
    suspend fun getNowPlayingMoviePreviews(): GetListMoviePreviewsResponseDTO

    suspend fun getSelectedMovieOverview(movieId: Long): GetMovieOverviewResponseDTO
    suspend fun getSelectedMovieReview(movieId: Long): GetMovieReviewsResponseDTO
}