package com.jordillonch.orders.context.order.domain

import com.jordillonch.commons.event_sourcing.domain.AggregateLifeCycle
import com.jordillonch.commons.event_sourcing.infrastructure.AbstractAggregate
import com.jordillonch.orders.context.order.domain.creator.InvalidOrderDetailException
import com.jordillonch.orders.context.order.domain.creator.OrderCreatedEvent
import com.jordillonch.orders.context.order.domain.creator.OrderDetailAddedEvent
import com.jordillonch.orders.context.store.domain.StoreId

class Order : AbstractAggregate() {

    lateinit var id: OrderId private set
    lateinit var storeId: StoreId private set
    var orderDetails: List<OrderDetail> = emptyList()
        private set

    override fun id() = id

    companion object {
        fun create(id: OrderId, storeId: StoreId, list: List<OrderDetail>): Order {
            orderDetailsGuard(list)
            return Order()
                    .also { order ->
                        AggregateLifeCycle.applyEvent(order, OrderCreatedEvent(id.id, storeId.id))
                        list.forEach {
                            AggregateLifeCycle.applyEvent(order,
                                                          OrderDetailAddedEvent(
                                                                  id.id,
                                                                  it.lineNumber,
                                                                  it.sku))
                        }
                    }
        }

        private fun orderDetailsGuard(list: List<OrderDetail>) {
            list.forEachIndexed { index, orderDetail ->
                if (index + 1 != orderDetail.lineNumber) throw InvalidOrderDetailException.badLineNumber(orderDetail)
            }
        }
    }

    fun on(event: OrderCreatedEvent) {
        id = OrderId(event.id)
        storeId = StoreId(event.storeId)
    }

    fun on(event: OrderDetailAddedEvent) {
        orderDetails += OrderDetail(event.lineNumber, event.sku)
    }
}