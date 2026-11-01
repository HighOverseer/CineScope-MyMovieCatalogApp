package com.fajar.cinescope.core.data.dependency_injection

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.fajar.cinescope.core.data.datasource.local.implementation.db.FavoriteMovieDao
import com.fajar.cinescope.core.data.datasource.local.implementation.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase =
        Room.databaseBuilder(
            context = context,
            klass = MovieDatabase::class.java,
            name = MovieDatabase.DATABASE_NAME
        )
            .setDriver(AndroidSQLiteDriver())
            .build()

    @Provides
    fun provideFavoriteMovieDao(
        db: MovieDatabase
    ): FavoriteMovieDao = db.favoriteMovieDao()
}