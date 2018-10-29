package com.jordillonch.commons.event_sourcing.domain

import com.jordillonch.commons.cqrs.event.domain.Event

interface EventStore {
    fun <A : Aggregate> load(factory: () -> A, id: AggregateId): A
    fun append(aggregate: Aggregate, eventSequence: Int, event: Event)
}