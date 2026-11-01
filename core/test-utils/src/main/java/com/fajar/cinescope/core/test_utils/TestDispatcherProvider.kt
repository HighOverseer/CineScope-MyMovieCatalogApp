package com.fajar.cinescope.core.test_utils

import com.fajar.cinescope.core.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

class TestDispatcherProvider(
    val testCoroutineScheduler: TestCoroutineScheduler
) : DispatcherProvider {
    private val testDispatcher = StandardTestDispatcher(testCoroutineScheduler)

    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}