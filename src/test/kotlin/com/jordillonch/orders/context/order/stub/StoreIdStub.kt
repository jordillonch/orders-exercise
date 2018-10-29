package com.jordillonch.orders.context.order.stub

import com.jordillonch.commons.faker.Faker
import com.jordillonch.orders.context.store.domain.StoreId

object StoreIdStub {
    fun random() = StoreId(Faker.instance().number().numberBetween(Int.MIN_VALUE, Int.MAX_VALUE ))
}