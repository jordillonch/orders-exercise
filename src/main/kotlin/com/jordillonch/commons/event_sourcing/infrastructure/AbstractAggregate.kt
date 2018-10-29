package com.jordillonch.commons.event_sourcing.infrastructure

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.commons.event_sourcing.domain.Aggregate
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaType

// FIXME: it is using reflection, to improve for example adding cache
abstract class AbstractAggregate : Aggregate {
    private var currentEventSequence = 1

    override fun process(event: Event): Int {
        javaClass.kotlin.declaredFunctions
                .first {
                    it.name == "on" &&
                    it.parameters[1].type.javaType == event.javaClass
                }
                .call(this, event)

        return currentEventSequence++
    }
}