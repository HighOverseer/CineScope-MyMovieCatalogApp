package com.fajar.cinescope.core.data.dependency_injection

import com.fajar.cinescope.core.data.BuildConfig
import com.fajar.cinescope.core.data.datasource.local.implementation.mapper.LocalDataMapperToDomain
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.MovieImageUrlResolver
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.RemoteDataMapperToDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {

    @Provides
    @Singleton
    fun provideRemoteDataMapperToDomain(
        movieImageUrlResolver: MovieImageUrlResolver
    ): RemoteDataMapperToDomain = RemoteDataMapperToDomain(movieImageUrlResolver)

    @Provides
    @Singleton
    fun provideLocalDataMapperToDomain(): LocalDataMapperToDomain = LocalDataMapperToDomain()

    @Provides
    @Singleton
    fun provideMovieImageResolver(): MovieImageUrlResolver {
        return MovieImageUrlResolver(baseImageUrl = BuildConfig.BASE_IMAGE_URL)
    }
}