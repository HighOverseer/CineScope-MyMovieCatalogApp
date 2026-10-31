package com.fajar.cinescope.core.data.datasource.remote.implementations.network.common

import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import java.net.UnknownHostException

internal val mapStatusCodeToError = hashMapOf<Int, DataError>(
    HttpStatusCode.BadRequest.value to RootNetworkError.BAD_REQUEST,
    HttpStatusCode.Unauthorized.value to RootNetworkError.UNAUTHORIZED,
    HttpStatusCode.Forbidden.value to RootNetworkError.FORBIDDEN,
    HttpStatusCode.NotFound.value to RootNetworkError.NOT_FOUND,
    HttpStatusCode.Conflict.value to RootNetworkError.RESOURCE_CONFLICT,
    HttpStatusCode.InternalServerError.value to RootNetworkError.INTERNAL_SERVER_ERROR,
    HttpStatusCode.ServiceUnavailable.value to RootNetworkError.SERVER_UNAVAILABLE
)


internal val mapIOExceptionToError = hashMapOf(
    SocketTimeoutException::class to RootNetworkError.REQUEST_TIMEOUT,
    ConnectTimeoutException::class to RootNetworkError.NO_CONNECTIVITY_OR_SERVER_UNREACHABLE,
    HttpRequestTimeoutException::class to RootNetworkError.REQUEST_TIMEOUT,
    UnknownHostException::class to RootNetworkError.NO_CONNECTIVITY_OR_SERVER_UNREACHABLE
)


internal suspend inline fun <reified M, D> callApiFromNetwork(
    execute: suspend () -> M,
    mapResponseToResultData: (M) -> D,
): Result<D, DataError> {
    try {
        val response = execute()

        currentCoroutineContext().ensureActive()
        val resultData = mapResponseToResultData(response)
        return Result.Success(resultData)

    } catch (e: HttpException) {
        val error =
            mapStatusCodeToError[e.statusCode] ?: RootNetworkError.UNEXPECTED_ERROR
        return Result.Error(error)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()

        val error = mapIOExceptionToError[e::class] ?: RootNetworkError.UNEXPECTED_ERROR
        return Result.Error(error)
    }
}