package com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetMovieReviewsResponseDTO(

    @SerialName("results")
    val results: List<GetMovieReviewsResponseItemDTO?>? = null,
)

@Serializable
internal data class GetMovieReviewsResponseItemDTO(

    @SerialName("author_details")
    val authorDetails: AuthorDetailsDTO? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("content")
    val content: String? = null,

    )

@Serializable
internal data class AuthorDetailsDTO(

    @SerialName("rating")
    val rating: Double? = null,

    @SerialName("username")
    val username: String? = null,
)
