package com.fajar.cinescope.core.domain.model

data class Rating(
    private val relativeScore: Double,
    val maxScore: Double
) {
    val normalizedRelativeScore = relativeScore
        .coerceAtMost(maximumValue = maxScore)
}