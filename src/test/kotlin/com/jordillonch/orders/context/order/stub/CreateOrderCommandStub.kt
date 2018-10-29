package com.jordillonch.orders.context.order.stub

import com.jordillonch.commons.faker.Faker
import com.jordillonch.orders.context.order.domain.OrderId
import com.jordillonch.orders.context.order.domain.creator.CreateOrderCommand
import com.jordillonch.orders.context.order.domain.creator.CreateOrderLineCommand
import com.jordillonch.orders.context.store.domain.StoreId

object CreateOrderCommandStub {
    fun random(id: OrderId = OrderIdStub.random(),
               storeId: StoreId = StoreIdStub.random(),
               lines: List<CreateOrderLineCommand> = CreateOrderLineCommandStub.randomList()) =
            CreateOrderCommand(id.id, storeId.id, lines)
}

object CreateOrderLineCommandStub {
    fun random(lineNumber: Int = Faker.instance().number().numberBetween(Int.MIN_VALUE, Int.MAX_VALUE),
               sku: String = Faker.instance().idNumber().valid()) = CreateOrderLineCommand(lineNumber, sku)

    fun randomList(elementsInList: Int =
                           Faker.instance().number().numberBetween(0, 1000)): List<CreateOrderLineCommand> {
        return (1..elementsInList).map { CreateOrderLineCommandStub.random(lineNumber = it) }
    }
}