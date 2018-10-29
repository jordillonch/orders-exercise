package com.jordillonch.commons.spring

import com.jordillonch.commons.cqrs.command.domain.Command
import com.jordillonch.commons.cqrs.command.domain.CommandBus
import com.jordillonch.commons.cqrs.command.domain.CommandHandler
import javax.annotation.PostConstruct
import javax.inject.Inject

abstract class SpringCommandHandler<in C : Command> : CommandHandler<C> {
    @Inject
    private lateinit var commandBus: CommandBus

    @PostConstruct
    private fun register() = commandBus.registerHandler(this)
}