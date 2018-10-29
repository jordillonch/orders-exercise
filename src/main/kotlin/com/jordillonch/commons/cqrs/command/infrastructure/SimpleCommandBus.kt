package com.jordillonch.commons.cqrs.command.infrastructure

import com.jordillonch.commons.cqrs.command.domain.Command
import com.jordillonch.commons.cqrs.command.domain.CommandBus
import com.jordillonch.commons.cqrs.command.domain.CommandHandler
import com.jordillonch.commons.cqrs.command.domain.NoCommandHandlerFoundException
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

// TODO: replace with other implementation like Axon Framework
class SimpleCommandBus : CommandBus {
    private val handlers: MutableMap<String, CommandHandler<Command>> = mutableMapOf()

    override fun <C : Command> registerHandler(handler: CommandHandler<C>) {
        @Suppress("UNCHECKED_CAST")
        handlers[classFrom(handler)] = handler as CommandHandler<Command>
    }

    override fun handle(command: Command) {
        @Suppress("UNCHECKED_CAST")
        return handlers[command::class.qualifiedName]
                       ?.on(command)
               ?: throw NoCommandHandlerFoundException()
    }

    private fun <C : Command> classFrom(handler: CommandHandler<C>) =
            handler.javaClass.kotlin
                    .declaredFunctions
                    .firstFunctionNamedOn()
                    .mapParameterTypes()
                    .first { it.isSubclassOf(Command::class) }
                    .qualifiedName!!

    private fun Collection<KFunction<*>>.firstFunctionNamedOn() = first { it.name == "on" }

    private fun KFunction<*>.mapParameterTypes() = parameters.map { it.type.jvmErasure }
}
