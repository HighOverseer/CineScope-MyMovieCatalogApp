package com.fajar.cinescope.core.data.datasource.remote

import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result

interface MovieRemoteDataSource {
    suspend fun getMoviePreviews(
        movieCategory: MovieCategory
    ): Result<List<MoviePreviewsItemData>, DataError>
}