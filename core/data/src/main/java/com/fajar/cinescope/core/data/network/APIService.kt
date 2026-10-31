package com.fajar.cinescope.core.data.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

abstract class DefaultAPIService {
    suspend inline fun <reified T> HttpResponse.getBodyIfNotSuccessThrowHttpException(): T {
        return if (this.status.isSuccess()) {
            this.body()
        } else throw HttpException(this.status.value)
    }
}