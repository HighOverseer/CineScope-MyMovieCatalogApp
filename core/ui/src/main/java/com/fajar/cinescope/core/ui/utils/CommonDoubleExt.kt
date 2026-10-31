package com.fajar.cinescope.core.ui.utils

fun Double.formatDecimalPlaces(decimalPlaces: Int = 2): String {
    return "%.${decimalPlaces}f".format(this)
}

fun Double.toDisplayPercentage(): String {
    return formatDecimalPlaces(decimalPlaces = 2) + "%"
}

fun Double.calculateAfterPercentage(
    percentage: Double
): Double {
    val normalized = (percentage / 100)
    val result = (1 - normalized).coerceIn(0.0, 1.0) * this
    return result
}
