package com.jordillonch.commons.stub

import com.jordillonch.commons.faker.Faker

object BigDecimalStub {
    fun positive() = Faker.instance().number().randomDouble(2, 0, Int.MAX_VALUE).toBigDecimal()
}
