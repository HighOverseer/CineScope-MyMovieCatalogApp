package com.fajar.cinescope.core.data.datasource.local.implementation.db

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "Favorite_Movie")
internal data class FavoriteMovieEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("relative_score")
    val relativeScore: Double? = null,
    @ColumnInfo("max_score")
    val maxScore: Double? = null,
    @ColumnInfo("count")
    val count: Int? = null
)