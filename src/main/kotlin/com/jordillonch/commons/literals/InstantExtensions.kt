package com.jordillonch.commons.literals

import java.time.Instant

fun Instant.max(timestamp: Instant): Instant {
    return if (this > timestamp) this
    else timestamp
}