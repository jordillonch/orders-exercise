package com.jordillonch.commons.cqrs.command.domain

interface Command

interface CommandHandler<in C : Command> {
    fun on(command: C)
}

interface CommandBus {
    fun <C : Command> registerHandler(handler: CommandHandler<C>)
    fun handle(command: Command)
}

class NoCommandHandlerFoundException : RuntimeException()