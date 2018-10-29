package com.jordillonch.orders.context.order.domain

import com.jordillonch.commons.event_sourcing.domain.AggregateId

// I think it should be an UUID
data class OrderId(val id: Int): AggregateId {
    override fun id() = id.toString()
}