package com.fajar.cinescope.core.data.dependency_injection

import com.fajar.cinescope.core.data.BuildConfig
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.MapperToDomain
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.MovieImageUrlResolver
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
    fun provideMapperToDomain(
        movieImageUrlResolver: MovieImageUrlResolver
    ): MapperToDomain = MapperToDomain(movieImageUrlResolver)

    @Provides
    @Singleton
    fun provideMovieImageResolver(): MovieImageUrlResolver {
        return MovieImageUrlResolver(baseImageUrl = BuildConfig.BASE_IMAGE_URL)
    }
}