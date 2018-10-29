package com.jordillonch.commons.cqrs.event.infrastructure

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.commons.cqrs.event.domain.EventBus
import com.jordillonch.commons.cqrs.event.domain.EventHandler
import java.util.concurrent.Executors

// TODO: replace with other implementation like Axon Framework
// Not used because introduces eventual consistency and tests are failing because after a POST of a transaction
// it is getting statistics. Besides it must be replied with a 202 HTTP status code.
class AsyncSimpleEventBus(poolSize: Int = 4) : EventBus {
    private val simpleEventBus = SimpleEventBus()
    private val executor = Executors.newFixedThreadPool(poolSize)

    override fun <C : Event> registerHandler(handler: EventHandler<C>) {
        simpleEventBus.registerHandler(handler)
    }

    override fun publish(event: Event) {
        executor.submit {
            simpleEventBus.publish(event)
        }
    }

    override fun publish(events: List<Event>) {
        simpleEventBus.publish(events)
    }
}
