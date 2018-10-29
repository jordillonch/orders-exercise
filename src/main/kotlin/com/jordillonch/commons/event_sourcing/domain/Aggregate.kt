package com.jordillonch.commons.event_sourcing.domain

import com.jordillonch.commons.cqrs.event.domain.Event

interface Aggregate {
    fun id(): AggregateId
    fun process(event: Event): Int
}