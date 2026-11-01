package com.fajar.cinescope.core.data.datasource.local.implementation.db

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FavoriteMovieDao {

    @Query("SELECT * FROM Favorite_Movie")
    fun getListFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(entity: FavoriteMovieEntity)

    @Query("DELETE FROM Favorite_Movie WHERE id = :movieId")
    suspend fun deleteFromListFavorite(movieId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite_Movie WHERE id = :movieId)")
    fun checkIfMovieInFavoriteList(movieId: Long): Flow<Boolean>
}