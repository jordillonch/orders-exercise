package com.jordillonch.commons.cqrs.query

import com.jordillonch.commons.assertions.assertFailsWith
import com.jordillonch.commons.cqrs.query.domain.NoQueryHandlerFoundException
import com.jordillonch.commons.cqrs.query.domain.Query
import com.jordillonch.commons.cqrs.query.domain.QueryHandler
import com.jordillonch.commons.cqrs.query.infrastructure.SimpleQueryBus
import com.jordillonch.commons.faker.Faker
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SimpleQueryBusTest {
    @Test
    fun `it should register a handler and then query it`() {
        val bus = SimpleQueryBus()

        bus.registerHandler(TestQueryHandler())

        val testValue = Faker.instance().number().randomNumber()
        val query = TestQuery(testValue)

        assertThat(bus.ask(query), equalTo(testValue))
    }

    @Test
    fun `it should fail because no registered handler`() {
        val bus = SimpleQueryBus()
        assertFailsWith(NoQueryHandlerFoundException::class) {
            bus.ask<Int>(TestQuery(1))
        }
    }

    private data class TestQuery(val id: Long) : Query

    private class TestQueryHandler : QueryHandler<TestQuery, Long> {
        override fun on(query: TestQuery): Long {
            return query.id
        }
    }
}