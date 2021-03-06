package com.jordillonch.commons.event_sourcing.infrastructure

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.commons.cqrs.event.domain.EventBus
import com.jordillonch.commons.event_sourcing.domain.Aggregate
import com.jordillonch.commons.event_sourcing.domain.AggregateId
import com.jordillonch.commons.event_sourcing.domain.DuplicateAggregateException
import com.jordillonch.commons.event_sourcing.domain.EventStore
import java.util.concurrent.ConcurrentHashMap

// this implementation has coupled the event store and the event bus,
// it is possible to implement a event store that just write to a database and use other
// implementation to track in the infrastructure side new events in order to publish to an
// event bus
class InMemoryEventStoreWithPublishToEventBus(private val eventBus: EventBus) : EventStore {
    private val store: ConcurrentHashMap<AggregateId, MutableList<Event>> = ConcurrentHashMap()

    override fun <A : Aggregate> load(factory: () -> A, id: AggregateId): A {
        val events = store[id]
        return factory()
                .also { events!!.forEach { event -> it.process(event) } }
    }

    override fun append(aggregate: Aggregate, eventSequence: Int, event: Event) {
        store.compute(aggregate.id()) { _, eventsList ->
            guardDuplicatedAggregate(eventSequence, eventsList, aggregate, event)
            eventsList?.also { it.add(event) } ?: mutableListOf(event)
        }

        eventBus.publish(event)
    }

    private fun guardDuplicatedAggregate(eventSequence: Int,
                                         eventsList: MutableList<Event>?,
                                         aggregate: Aggregate,
                                         event: Event) {
        if (eventSequence == 1 && eventsList != null)
            throw DuplicateAggregateException.appendingFirstEvent(aggregate, event)
    }
}