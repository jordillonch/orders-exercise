package com.jordillonch.orders.context.order.domain.creator

import com.jordillonch.commons.cqrs.event.domain.Event
import com.jordillonch.orders.context.order.domain.Sku

data class OrderDetailAddedEvent(val id: Int, val lineNumber: Int, val sku: Sku) : Event