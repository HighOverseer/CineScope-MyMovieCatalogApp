package com.fajar.cinescope.core.data.datasource.remote.implementations.network.common

internal class HttpException(
    val statusCode: Int
) : Exception()