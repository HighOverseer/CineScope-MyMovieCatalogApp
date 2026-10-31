package com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper

class MovieImageUrlResolver(
    private val baseImageUrl: String
) {
    fun resolve(
        path: String?
    ): String? {
        return path?.let { "${baseImageUrl}$it" }
    }
}