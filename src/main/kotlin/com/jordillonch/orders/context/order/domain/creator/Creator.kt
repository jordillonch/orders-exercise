package com.jordillonch.orders.context.order.domain.creator

import com.jordillonch.orders.context.order.domain.Order
import com.jordillonch.orders.context.order.domain.OrderDetail
import com.jordillonch.orders.context.order.domain.OrderId
import com.jordillonch.orders.context.store.domain.StoreId
import org.springframework.stereotype.Service

@Service
class Creator {

    operator fun invoke(id: OrderId, storeId: StoreId, list: List<OrderDetail>) {
        Order.create(id, storeId, list)
    }
}