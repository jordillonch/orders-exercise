package com.jordillonch.orders.context.order.adapter.controller.create

import com.jordillonch.orders.context.order.domain.creator.CreateOrderCommand
import com.jordillonch.orders.context.order.domain.creator.CreateOrderLineCommand

data class OrderRestRequest(val order: OrderDetailRestRequest)
data class OrderDetailRestRequest(val id: Int, val storeId: Int, val lines: List<OrderDetailLinesRestRequest>)
data class OrderDetailLinesRestRequest(val lineNumber: Int, val sku: String)

fun OrderRestRequest.toCommand() =
        CreateOrderCommand(order.id,
                           order.storeId,
                           order.lines.map { CreateOrderLineCommand(it.lineNumber, it.sku) })
