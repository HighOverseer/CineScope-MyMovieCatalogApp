package com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetMovieOverviewResponseDTO(

    @SerialName("title")
    val title: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null

)