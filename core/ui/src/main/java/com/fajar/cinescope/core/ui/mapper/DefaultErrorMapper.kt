package com.fajar.cinescope.core.ui.mapper

import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.Error
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootError
import com.fajar.cinescope.core.domain.model.RootNetworkError
import com.fajar.cinescope.core.ui.R
import com.fajar.cinescope.core.ui.model.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DefaultErrorMapper @Inject constructor() {
    private val mapNetworkErrorToStringResource = hashMapOf(
        RootNetworkError.UNEXPECTED_ERROR to R.string.an_error_occurred_please_try_again,
        RootNetworkError.CONNECTIVITY_UNAVAILABLE to R.string.an_error_occurred_please_check_your_connectivity,
        RootNetworkError.NO_CONNECTIVITY_OR_SERVER_UNREACHABLE to R.string.sorry_something_went_wrong_please_check_your_connection,
        RootNetworkError.REQUEST_TIMEOUT to R.string.an_error_occurred_check_your_connectivity_or_try_again_later,
        RootNetworkError.BAD_REQUEST to R.string.an_error_occurred_please_try_again,
        RootNetworkError.FORBIDDEN to R.string.invalid_session_please_try_again,
        RootNetworkError.UNAUTHORIZED to R.string.invalid_session_please_try_again,
        RootNetworkError.SERVER_UNAVAILABLE to R.string.sorry_the_server_seems_busy_please_try_again_later,
        RootNetworkError.INTERNAL_SERVER_ERROR to R.string.an_error_occurred_please_try_again_later
    )

    private fun getMapper(error: Error): HashMap<out Error, Int>? {
        return when (error) {
            is RootNetworkError -> mapNetworkErrorToStringResource
            else -> null
        }
    }

    open fun <T : RootError> getErrorStringResource(resultError: Result.Error<T>): UIText {
        return getErrorStringResource(resultError.error)
    }

    fun <T : RootError> getErrorStringResource(error: T): UIText {
        if (error is DataError.Unexpected) {
            error.reason?.let { return UIText.Dynamic(it) }
        }

        val defaultStatic = UIText.Static(R.string.an_error_occurred_please_try_again)
        val correspondingMap = getMapper(error)
            ?: return defaultStatic

        val stringResource = correspondingMap[error] ?: return defaultStatic
        return UIText.Static(stringResource)
    }
}