package com.fajar.cinescope.core.data.datasource.remote.implementations.network.common


import com.fajar.cinescope.core.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class KtorClient(
    httpClientEngine: HttpClientEngine = OkHttp.create(),
) {

    val client: HttpClient = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(
                contentType = ContentType.Application.Json,
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = true
                }
            )
        }

        if (BuildConfig.DEBUG) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
        }

        defaultRequest {
            url(BuildConfig.BASE_URL)
        }
    }
}
