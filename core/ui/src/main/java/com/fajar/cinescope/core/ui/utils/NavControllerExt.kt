package com.fajar.cinescope.core.ui.utils

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

fun NavController.navigateUpSafely() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigateUp()
    }
}

fun <T : Any> NavController.navigateSafely(
    route: T
) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route)
    }
}