package com.jordillonch.commons.stub

import com.jordillonch.commons.faker.Faker
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

object InstantStub {
    fun past(atMost: Int = Int.MAX_VALUE,
             unit: TimeUnit = TimeUnit.MILLISECONDS) =
            Faker.instance().date().past(atMost, unit).toInstant()!!

    fun pastFrom(from: Instant,
                 atMost: Int = Int.MAX_VALUE,
                 unit: ChronoUnit = ChronoUnit.MILLIS) =
            from.minus(atMost.toLong(), unit)!!

    fun future(atMost: Int = Int.MAX_VALUE,
               unit: TimeUnit = TimeUnit.MILLISECONDS) =
            Faker.instance().date().future(atMost, unit).toInstant()!!
}