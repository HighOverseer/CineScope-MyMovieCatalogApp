package com.fajar.cinescope.core.ui.utils

fun Double.formatDecimalPlaces(decimalPlaces: Int = 2): String {
    return "%.${decimalPlaces}f".format(this)
}