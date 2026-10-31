package com.fajar.cinescope.core.ui.utils.formatter

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DefaultDUIDateFormatter @Inject constructor() {

    private val defaultDateFormat by lazy {
        SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.getDefault())
    }

    open fun formatToDisplayDate(
        timestamp: Long
    ): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return defaultDateFormat.format(calendar.time)
    }

    companion object {
        private const val DEFAULT_DATE_PATTERN = "dd MMM yyy"
    }
}