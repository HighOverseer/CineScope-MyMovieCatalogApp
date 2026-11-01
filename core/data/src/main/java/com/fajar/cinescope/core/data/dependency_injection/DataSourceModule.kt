package com.fajar.cinescope.core.data.dependency_injection

import com.fajar.cinescope.core.data.datasource.local.MovieLocalDataSource
import com.fajar.cinescope.core.data.datasource.local.implementation.MovieLocalDataSourceImpl
import com.fajar.cinescope.core.data.datasource.remote.MovieRemoteDataSource
import com.fajar.cinescope.core.data.datasource.remote.implementations.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(
        remoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(
        localDataSourceImpl: MovieLocalDataSourceImpl
    ): MovieLocalDataSource
}