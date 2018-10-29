package com.jordillonch.commons.cqrs.command

import com.jordillonch.commons.assertions.assertFailsWith
import com.jordillonch.commons.cqrs.command.domain.Command
import com.jordillonch.commons.cqrs.command.domain.CommandHandler
import com.jordillonch.commons.cqrs.command.domain.NoCommandHandlerFoundException
import com.jordillonch.commons.cqrs.command.infrastructure.SimpleCommandBus
import com.jordillonch.commons.faker.Faker
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SimpleCommandBusTest {
    @Test
    fun `it should register a handler and then send a command it`() {
        val bus = SimpleCommandBus()

        val handler = TestCommandHandler()
        bus.registerHandler(handler)

        val testValue = Faker.instance().number().randomNumber()
        val command = TestCommand(testValue)

        bus.handle(command)
        assertThat(testValue, equalTo(handler.testValueToAssert))
    }

    @Test
    fun `it should fail because no registered handler`() {
        val bus = SimpleCommandBus()
        assertFailsWith(NoCommandHandlerFoundException::class) {
            bus.handle(TestCommand(1))
        }
    }

    private data class TestCommand(val id: Long) : Command

    private class TestCommandHandler : CommandHandler<TestCommand> {
        var testValueToAssert: Long? = null

        override fun on(command: TestCommand) {
            testValueToAssert = command.id
        }
    }
}