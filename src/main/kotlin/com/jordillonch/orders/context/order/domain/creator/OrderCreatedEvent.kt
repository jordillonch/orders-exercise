package com.jordillonch.orders.context.order.domain.creator

import com.jordillonch.commons.cqrs.event.domain.Event

data class OrderCreatedEvent(val id: Int,
                             val storeId: Int) : Event