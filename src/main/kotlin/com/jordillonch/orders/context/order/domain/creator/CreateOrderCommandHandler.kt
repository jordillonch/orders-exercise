package com.jordillonch.orders.context.order.domain.creator

import com.jordillonch.commons.spring.SpringCommandHandler
import com.jordillonch.orders.context.order.domain.OrderDetail
import com.jordillonch.orders.context.order.domain.OrderId
import com.jordillonch.orders.context.order.domain.Sku
import com.jordillonch.orders.context.store.domain.StoreId
import org.springframework.stereotype.Service

@Service
class CreateOrderCommandHandler(private val create: Creator) : SpringCommandHandler<CreateOrderCommand>() {

    override fun on(command: CreateOrderCommand) =
            create(OrderId(command.id),
                   StoreId(command.storeId),
                   command.lines.map { OrderDetail(it.lineNumber, Sku(it.sku)) })
}