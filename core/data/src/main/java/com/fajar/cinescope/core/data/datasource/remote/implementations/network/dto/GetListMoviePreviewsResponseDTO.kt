package com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetListMoviePreviewsResponseDTO(

    @SerialName("results")
    val results: List<MoviePreviewsResponseItemDTO>? = null

)

@Serializable
internal data class MoviePreviewsResponseItemDTO(

    @SerialName("title")
    val title: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null
)
