package com.jordillonch.orders.context.notification.email.domain.sender

import com.jordillonch.commons.spring.SpringEventHandler
import com.jordillonch.orders.context.order.domain.OrderId
import com.jordillonch.orders.context.order.domain.creator.OrderCreatedEvent
import org.springframework.stereotype.Component

// The ideal solution would be to publish events to a message broker like RabbitMQ. Then we would consume the events
// in a way that it would be retried if something fails.
@Component
class SendEmailOnOrderCreatedEventHandler(private val sender: Sender) : SpringEventHandler<OrderCreatedEvent>() {
    override fun on(event: OrderCreatedEvent) {
        sender(OrderId(event.id))
    }
}