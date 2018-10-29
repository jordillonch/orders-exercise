package com.jordillonch.orders.context.order.stub

import com.jordillonch.commons.faker.Faker
import com.jordillonch.orders.context.order.adapter.controller.create.OrderDetailLinesRestRequest
import com.jordillonch.orders.context.order.adapter.controller.create.OrderDetailRestRequest
import com.jordillonch.orders.context.order.adapter.controller.create.OrderRestRequest
import com.jordillonch.orders.context.order.domain.OrderId
import com.jordillonch.orders.context.store.domain.StoreId

object OrderRestRequestStub {
    fun random(id: OrderId = OrderIdStub.random(),
               storeId: StoreId = StoreIdStub.random(),
               lines: List<OrderDetailLinesRestRequest> = OrderDetailLinesRestRequestStub.randomList()) =
            OrderRestRequest(OrderDetailRestRequest(id.id, storeId.id, lines))
}

object OrderDetailLinesRestRequestStub {
    fun random(lineNumber: Int = Faker.instance().number().numberBetween(Int.MIN_VALUE, Int.MAX_VALUE),
               sku: String = Faker.instance().idNumber().valid()) = OrderDetailLinesRestRequest(lineNumber, sku)

    fun randomList(elementsInList: Int =
                           Faker.instance().number().numberBetween(0, 1000)): List<OrderDetailLinesRestRequest> {
        return (1..elementsInList).map { OrderDetailLinesRestRequestStub.random(lineNumber = it) }
    }
}