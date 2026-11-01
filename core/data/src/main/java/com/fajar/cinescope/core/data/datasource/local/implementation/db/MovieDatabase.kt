package com.fajar.cinescope.core.data.datasource.local.implementation.db

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [FavoriteMovieEntity::class], version = 1)
internal abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        internal const val DATABASE_NAME = "movie.db"
    }
}