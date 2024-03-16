package com.example.test.util

import com.example.core.util.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatchersProvider(
    val standardTestDispatcher: TestDispatcher = StandardTestDispatcher()
): DispatcherProvider {
    override val io: CoroutineDispatcher
        get() = standardTestDispatcher
    override val main: CoroutineDispatcher
        get() = standardTestDispatcher
    override val default: CoroutineDispatcher
        get() = standardTestDispatcher
    override val unconfined: CoroutineDispatcher
        get() = standardTestDispatcher
}