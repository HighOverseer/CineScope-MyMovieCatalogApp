package com.fajar.cinescope.core.ui.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

fun Context.startShareIntent(
    subject: String,
    text: String,
    chooserTitle: String
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }

    try {
        this.startActivity(
            Intent.createChooser(intent, chooserTitle)
        )
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}