package com.friosteven.rosebedsheet_kmp.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//TODO: FOR MOCKING BUT CAN'T MAKE IT WORK IN IOS
/**
 * Creates a simple [StateFlow] for use in tests or Swift previews,
 * initialized with the given value.
 */
fun <T> createMockStateFlow(initialValue: T): StateFlow<T> {
    return MutableStateFlow(initialValue)
    // We return it as a read-only StateFlow to match the interface,
    // even though we create it with a MutableStateFlow.
}

/**
 * An alternative helper that returns the MutableStateFlow itself.
 * This can be more useful for mocks where you might want to change the
 * value later during a test.
 */
fun <T> createMutableMockStateFlow(initialValue: T): MutableStateFlow<T> {
    return MutableStateFlow(initialValue)
}