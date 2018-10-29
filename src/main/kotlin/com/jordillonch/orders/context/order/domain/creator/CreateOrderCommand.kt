package com.jordillonch.orders.context.order.domain.creator

import com.jordillonch.commons.cqrs.command.domain.Command

data class CreateOrderCommand(val id: Int, val storeId: Int, val lines: List<CreateOrderLineCommand>) : Command
data class CreateOrderLineCommand(val lineNumber: Int, val sku: String)