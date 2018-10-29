package com.jordillonch.orders.context.order.behaviour

import com.jordillonch.commons.assertions.assertFailsWith
import com.jordillonch.commons.cqrs.event.infrastructure.SimpleEventBus
import com.jordillonch.commons.event_sourcing.domain.AggregateLifeCycle
import com.jordillonch.commons.event_sourcing.domain.DuplicateAggregateException
import com.jordillonch.commons.event_sourcing.infrastructure.InMemoryEventStoreWithPublishToEventBus
import com.jordillonch.orders.context.order.domain.Order
import com.jordillonch.orders.context.order.domain.OrderId
import com.jordillonch.orders.context.order.domain.creator.CreateOrderCommandHandler
import com.jordillonch.orders.context.order.domain.creator.Creator
import com.jordillonch.orders.context.order.domain.creator.InvalidOrderDetailException
import com.jordillonch.orders.context.order.stub.CreateOrderCommandStub
import com.jordillonch.orders.context.order.stub.CreateOrderLineCommandStub
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CreatorTest {
    private val eventBus = SimpleEventBus()
    private val eventStore = InMemoryEventStoreWithPublishToEventBus(eventBus)
            .also { AggregateLifeCycle.init(it) }
    private val creator = Creator()
    private val handler = CreateOrderCommandHandler(creator)

    @Test
    fun `it should create an order`() {
        val command = CreateOrderCommandStub.random()
        handler.on(command)

        val loadedOrder = eventStore.load(::Order, OrderId(command.id))

        assertThat(loadedOrder.id.id, equalTo(command.id))
    }

    @Test
    fun `it should fail because invalid order detail line number`() {
        val command = CreateOrderCommandStub.random(lines = (2..5).map { CreateOrderLineCommandStub.random(lineNumber = it) })
        assertFailsWith(InvalidOrderDetailException::class) {
            handler.on(command)
        }
    }

    @Test
    fun `it should fail because duplicated order`() {
        val command = CreateOrderCommandStub.random()
        handler.on(command)
        assertFailsWith(DuplicateAggregateException::class) {
            handler.on(command)
        }
    }

}