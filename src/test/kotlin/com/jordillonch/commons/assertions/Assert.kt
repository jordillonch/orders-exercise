package com.jordillonch.commons.assertions

import kotlin.reflect.KClass

/** Asserts that a [block] fails with a specific exception being thrown. */
private fun <T : Throwable> assertFailsWithImpl(exceptionClass: Class<T>, message: String?, block: () -> Unit): T {
    try {
        block()
    } catch (e: Throwable) {
        if (exceptionClass.isInstance(e)) {
            @Suppress("UNCHECKED_CAST")
            return e as T
        }

        throw TestFailedException(messagePrefix(
                message) + "Expected an exception of type $exceptionClass to be thrown, but was $e")
    }

    val msg = messagePrefix(message)
    throw TestFailedException(msg + "Expected an exception of type $exceptionClass to be thrown, but was completed successfully.")
}

/** Asserts that a [block] fails with a specific exception of type [exceptionClass] being thrown. */
fun <T : Throwable> assertFailsWith(exceptionClass: KClass<T>, block: () -> Unit): T = assertFailsWith(
        exceptionClass,
        null,
        block)

/** Asserts that a [block] fails with a specific exception of type [exceptionClass] being thrown. */
fun <T : Throwable> assertFailsWith(exceptionClass: KClass<T>, message: String?, block: () -> Unit): T = assertFailsWithImpl(
        exceptionClass.java,
        message,
        block)

/** Asserts that a [block] fails with a specific exception of type [T] being thrown.
 *  Since inline method doesn't allow to trace where it was invoked, it is required to pass a [message] to distinguish this method call from others.
 */
inline fun <reified T : Throwable> assertFailsWith(message: String? = null, noinline block: () -> Unit): T = assertFailsWith(
        T::class,
        message,
        block)

private fun messagePrefix(message: String?): String {
    return if (message == null) "" else "$message. "
}

class TestFailedException(message: String): RuntimeException(message)