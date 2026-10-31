package com.fajar.cinescope.core.data.dependency_injection

import com.fajar.cinescope.core.data.datasource.remote.implementations.network.MovieAPIService
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.MovieAPIServiceImpl
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.common.KtorClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @Binds
    abstract fun bindMovieAPIService(
        impl: MovieAPIServiceImpl
    ): MovieAPIService

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return KtorClient().client
        }
    }
}