package com.jordillonch.orders.context.order.stub

import com.jordillonch.commons.faker.Faker
import com.jordillonch.orders.context.order.domain.OrderId

object OrderIdStub {
    fun random() = OrderId(Faker.instance().number().numberBetween(Int.MIN_VALUE, Int.MAX_VALUE ))
}